
package com.alumni_searcher.alumni.service;

import com.alumni_searcher.alumni.client.PhantomBusterClient;
import com.alumni_searcher.alumni.dto.RequestDTO;
import com.alumni_searcher.alumni.dto.ResponseDTO;
import com.alumni_searcher.alumni.entity.Alumni;
import com.alumni_searcher.alumni.mapper.Mapper;
import com.alumni_searcher.alumni.repository.AlumniRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static com.alumni_searcher.alumni.mapper.Mapper.entityToRes;
import static com.alumni_searcher.alumni.mapper.Mapper.resToEntity;

@Service
public class AlumniServiceImpl implements AlumniService {

    private final AlumniRepository alumniRepository;
    private final PhantomBusterClient phantomBusterClient;
    private final ObjectMapper objectMapper;

    public AlumniServiceImpl(
            AlumniRepository alumniRepository,
            PhantomBusterClient phantomBusterClient,
            ObjectMapper objectMapper
    ) {
        this.alumniRepository = alumniRepository;
        this.phantomBusterClient = phantomBusterClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ResponseDTO> findAllusers() {

        List<Alumni> alumni = alumniRepository.findAll();

        return alumni.stream()
                .map(Mapper::entityToRes)
                .toList();
    }

    @Override
    public List<ResponseDTO> search(RequestDTO request) {

        // Step 1: Launch PhantomBuster
        JsonNode launchResponse =
                phantomBusterClient.LunchPhantom(request);

        if (launchResponse == null
                || launchResponse.get("containerId") == null
                || launchResponse.get("containerId").isNull()) {
            throw new RuntimeException("PhantomBuster launch failed");
        }

        String containerId =
                launchResponse.get("containerId").asString();

        boolean finalized = false;

        for (int attempt = 1; attempt <= 120; attempt++) {

            JsonNode containers =
                    phantomBusterClient.fetchFinalizedContainers();

            if (phantomBusterClient.containsContainer(containers, containerId)) {
                finalized = true;
                break;
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Search interrupted", ex);
            }
        }

        if (!finalized) {
            throw new RuntimeException("PhantomBuster execution timed out");
        }

        // fetch the alumni list
        JsonNode result = phantomBusterClient.fetchResult(containerId);

        if (result == null
                || result.get("resultObject") == null
                || result.get("resultObject").isNull()) {
            throw new RuntimeException("Unable to fetch alumni profiles");
        }

        JsonNode rawResult = result.get("resultObject");

        if (!rawResult.isString() || rawResult.asString().isBlank()) {
            throw new RuntimeException("PhantomBuster returned an invalid result");
        }

        JsonNode resultObject;

        try {
            resultObject = objectMapper.readTree(rawResult.asString());
        } catch (Exception ex) {
            throw new RuntimeException("Unable to parse PhantomBuster result", ex);
        }

        if (resultObject == null || !resultObject.isArray()) {
            throw new RuntimeException("Unexpected PhantomBuster result format");
        }

        if (resultObject.isEmpty()) {
            throw new RuntimeException("No alumni profiles found");
        }

        // Step 4: Convert JSON into Alumni entities
        List<Alumni> alumniList = resultObject.valueStream()
                .filter(item ->
                        item.get("profileUrl") != null
                                && !item.get("profileUrl").isNull()
                )
                .filter(item ->
                        !alumniRepository.existsByLinkedinUrl(
                                item.get("profileUrl").asString()
                        )
                )
                .map(item -> resToEntity(item, request))
                .toList();

        if (alumniList.isEmpty()) {
            throw new RuntimeException("No new Alumni found, fetched Alumni already exists");
        }

        List<Alumni> filteredAlumni = alumniList.stream()
                .filter(x -> x.getCurrentRole() != null)
                .toList();

        return alumniRepository.saveAll(filteredAlumni)
                .stream()
                .map(Mapper::entityToRes)
                .toList();
    }

    @Override
    public void deleteAlumni(Long id) {

        if (!alumniRepository.existsById(id)) {
            throw new RuntimeException("Alumni not found with id: " + id);
        }

        alumniRepository.deleteById(id);
    }

    @Override
    public void deleteAllAlumni() {

        alumniRepository.deleteAll();
    }
}
