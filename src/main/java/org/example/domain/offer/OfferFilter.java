package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.stream.Collectors;

class OfferFilter {
    static List<OfferDto> filterOffers(List<OfferDto> offerFetcherDtos, List<String> offerUrl) {
        return offerFetcherDtos.stream()
                .filter(offer -> !offerUrl.contains(offer.offerUrl()))
                .collect(Collectors.toList());
    }
}
