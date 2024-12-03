package org.example.infrastructure.offer.http;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.offer.OfferFetchable;
import org.example.domain.offer.dto.OfferDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<OfferDto> fetchOffers() {
        log.info("Start fetching offers using http service");
        String urlForService = getUrlForService("/offers");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        try{
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                    .toUriString();
            ResponseEntity<List<OfferDto>> exchange = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            final List<OfferDto> body = exchange.getBody();

            if(body == null) {
                log.warn("Fetching 0 offers, body was empty");
                return Collections.emptyList();
            }
            log.info("Success response offer have Offers");
            return body;
        } catch (ResourceAccessException e ) {
            log.error("Error while fetching offers! " + e.getMessage());
            return Collections.emptyList();
        }

    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
