package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;

import java.util.List;

public class OfferFeatcherTestImpl implements OfferFeatchable {
    @Override
    public List<OfferDto> fetchOffers() {
        return List.of(
                new OfferDto("aaa", "junior", "abba", "7000-9000", "1"),
                new OfferDto("aaa", "junior", "abba", "7000-9000", "2"),
                new OfferDto("aaa", "junior", "abba", "7000-9000", "3"),
                new OfferDto("aaa", "junior", "abba", "7000-9000", "4"),
                new OfferDto("aaa", "junior", "abba", "7000-9000", "5"),
                new OfferDto("aaa", "junior", "abba", "7000-9000", "6")
        );
    }
}
