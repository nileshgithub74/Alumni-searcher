package com.alumni_searcher.alumni.service;

import com.alumni_searcher.alumni.client.PhantomBusterClient;
import com.alumni_searcher.alumni.dto.RequestDTO;
import tools.jackson.databind.JsonNode;

public class PhantomBusterService {
    private final PhantomBusterClient phantomBusterClient;

    public PhantomBusterService(PhantomBusterClient phantomBusterClient) {
        this.phantomBusterClient = phantomBusterClient;
    }

    // launch the phantomBuster ;

    public JsonNode launchSearchService(RequestDTO requestDTO) {

        return phantomBusterClient.LunchPhantom(requestDTO);
    }

    public JsonNode fetchResult(String id) {

        return phantomBusterClient.fetchResult(id);
    }

}
