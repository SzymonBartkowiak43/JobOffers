package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;

import java.util.List;

public class OfferFetcherTestImpl implements OfferFetchable {
    @Override
    public List<OfferDto> fetchOffers() {
        return List.of(
                new OfferDto("aaa",  "abba", "7000-9000", "1"),
                new OfferDto("aaa", "abba", "7000-9000", "2"),
                new OfferDto("aaa",  "abba", "7000-9000", "3"),
                new OfferDto("aaa",  "abba", "7000-9000", "4"),
                new OfferDto("aaa", "abba", "7000-9000", "5"),
                new OfferDto("aaa", "abba", "7000-9000", "6")
        );
    }
}
