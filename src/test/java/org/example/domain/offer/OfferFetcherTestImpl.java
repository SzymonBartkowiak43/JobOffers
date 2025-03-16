package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.List;

public class OfferFetcherTestImpl implements OfferFetchable {
    @Override
    public List<OfferDto> fetchOffers() {
        return List.of(
                new OfferDto("aaa",  "a", "Warszawa", "Hybrydowy", "2 000 - 3 000", "BlueSoft", List.of("Java"), SourceSystem.valueOf("AKMF"), LocalDateTime.of(2021, 10, 10, 10, 10)),
                new OfferDto("aaa",  "ab", "Warszawa", "Hybrydowy", "2 000 - 3 000", "BlueSoft", List.of("Java"), SourceSystem.valueOf("AKMF"), LocalDateTime.of(2021, 10, 10, 10, 10)),
                new OfferDto("aaa",  "abb", "Warszawa", "Hybrydowy", "2 000 - 3 000", "BlueSoft", List.of("Java"), SourceSystem.valueOf("AKMF"), LocalDateTime.of(2021, 10, 10, 10, 10)),
                new OfferDto("aaa",  "abba", "Warszawa", "Hybrydowy", "2 000 - 3 000", "BlueSoft", List.of("Java"), SourceSystem.valueOf("AKMF"), LocalDateTime.of(2021, 10, 10, 10, 10)),
                new OfferDto("aaa",  "abbab", "Warszawa", "Hybrydowy", "2 000 - 3 000", "BlueSoft", List.of("Java"), SourceSystem.valueOf("AKMF"), LocalDateTime.of(2021, 10, 10, 10, 10)),
                new OfferDto("aaa",  "abbabba", "Warszawa", "Hybrydowy", "2 000 - 3 000", "BlueSoft", List.of("Java"), SourceSystem.valueOf("AKMF"), LocalDateTime.of(2021, 10, 10, 10, 10))
        );
    }
}
