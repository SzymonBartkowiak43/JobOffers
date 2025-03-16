package org.example.infrastructure.offer.http;

import org.example.domain.offer.OfferFetchable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferFetcherConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${jobOffer.offer-fetchable.http.client.config.connectionTimeout:10000}") long connectionTimeout,
                                     @Value("${jobOffer.offer-fetchable.http.client.config.readTimeout:30000}") long readTimeout,
                                     RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public OfferFetchable remoteOfferFetchable(RestTemplate restTemplate,
                                               @Value("${jobOffer.offer-fetchable.http.client.config.uri}") String uri,
                                               @Value("${jobOffer.offer-fetchable.http.client.config.port}")int port) {
        return new OfferFetcherRestTemplate(restTemplate,uri,port);
    }

}
