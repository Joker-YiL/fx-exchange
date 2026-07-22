package com.fx.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AckClient {

    private static final Logger log = LoggerFactory.getLogger(AckClient.class);

    private final RestTemplate http;
    private final String orchestratorUrl;

    public AckClient(RestTemplate http,
                     @Value("${fx.orchestrator.url:http://localhost:8081}") String orchestratorUrl) {
        this.http = http;
        this.orchestratorUrl = orchestratorUrl.endsWith("/")
                ? orchestratorUrl.substring(0, orchestratorUrl.length() - 1)
                : orchestratorUrl;
    }

    public void ack(long batchId, String status) {
        try {
            http.postForEntity(orchestratorUrl + "/api/feed/ack",
                    new AckMessage(batchId, status), Void.class);
        } catch (RuntimeException e) {
            log.warn("Could not ACK batch {} to orchestrator: {}", batchId, e.getMessage());
        }
    }

    private record AckMessage(long batchId, String status) {}
}
