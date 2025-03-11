package org.example.domain.HtmlPages.ports.input;

import org.example.domain.HtmlPages.response.JobOfferResponse;

import java.util.List;

public interface JobOfferProvider {
    List<JobOfferResponse> fetchOffers();
}