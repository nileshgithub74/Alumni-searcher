
package com.alumni_searcher.alumni.client;

import com.alumni_searcher.alumni.dto.RequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class PhantomBusterClient {

    private final WebClient webClient;
    private final String apikey;
    private final String phantomId;

    public PhantomBusterClient(
            WebClient.Builder builder,
            @Value("${phantomBuster.api-key}") String apikey,
            @Value("${phantomBuster.phantom-id}") String phantomId
    ) {
        this.apikey = apikey;
        this.phantomId = phantomId;

        this.webClient = builder
                .baseUrl("https://api.phantombuster.com/api/v2")
                .build();
    }

    // Launch PhantomBuster
    public JsonNode LunchPhantom(RequestDTO request) {


        Map<String, Object> body = new HashMap<>();
        body.put("id", phantomId);


        String keywords = "\""
                + request.getUniversity().trim()
                + "\" \""
                + request.getDesignation().trim()
                + "\"";

        String searchUrl =
                "https://www.linkedin.com/search/results/people/?keywords="
                        + URLEncoder.encode(
                        keywords,
                        StandardCharsets.UTF_8
                );


        Map<String, Object> bonusArgument = new HashMap<>();
        bonusArgument.put("linkedInSearchUrl", searchUrl);

        body.put("bonusArgument", bonusArgument);


        return webClient.post()
                .uri("/agents/launch")
                .header("X-Phantombuster-Key", apikey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    // fethc result
    public JsonNode fetchResult(String id) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/containers/fetch-result-object")
                        .queryParam("id", id)
                        .build()
                )
                .header("X-Phantombuster-Key", apikey)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    public JsonNode fetchFinalizedContainers() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/containers/fetch-all")
                        .queryParam("agentId", phantomId)
                        .queryParam("mode", "finalized")
                        .queryParam("limit", 20)
                        .build()
                )
                .header("X-Phantombuster-Key", apikey)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    public boolean containsContainer(
            JsonNode response,
            String containerId
    ) {

        if (response == null || response.isNull()) {
            return false;
        }

        JsonNode containers = response;

        if (!containers.isArray()) {
            if (containers.get("containers") != null) {
                containers = containers.get("containers");
            } else if (containers.get("data") != null) {
                containers = containers.get("data");
            }
        }

        if (!containers.isArray()) {
            return false;
        }

        return containers.valueStream().anyMatch(container -> {

            JsonNode id = container.get("id");

            return id != null
                    && !id.isNull()
                    && containerId.equals(id.asString());
        });
    }

}
