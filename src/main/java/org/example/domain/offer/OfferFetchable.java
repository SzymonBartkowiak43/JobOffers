package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;

import java.util.List;

public interface OfferFetchable {
    List<OfferDto> fetchOffers();
}
