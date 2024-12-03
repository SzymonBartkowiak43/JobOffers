package org.example.infrastructure.offer.http;


import lombok.AllArgsConstructor;
import org.example.domain.offer.OfferFetchable;
import org.example.domain.offer.dto.OfferDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
public class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<OfferDto> fetchOffers() {
        String urlForService = getUrlForService("/api/v1.0/offers");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .toUriString();
        ResponseEntity<List<OfferDto>> exchange = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return exchange.getBody();
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
