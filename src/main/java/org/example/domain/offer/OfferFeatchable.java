package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;

import java.util.List;

public interface OfferFeatchable {
    List<OfferDto> fetchOffers();
}
