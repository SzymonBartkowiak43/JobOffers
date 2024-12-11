package org.example.http.error;

import org.example.domain.offer.OfferFetchable;
import org.example.infrastructure.offer.http.OfferFetcherConfig;
import org.springframework.web.client.RestTemplate;

import static org.example.BaseIntegrationTest.WIRE_MOCK_HOST;

public class OfferFetcherRestTemplateTestConfig extends OfferFetcherConfig {

    public OfferFetchable romoteOfferTestClient(int port, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return remoteOfferFetchable(restTemplate, WIRE_MOCK_HOST, port);
    }
}
