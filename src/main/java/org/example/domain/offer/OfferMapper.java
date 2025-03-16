package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    static OfferDto mapOfferToOfferDto(Offer offer) {
        return OfferDto.builder()
                .title(offer.title())
                .offerUrl(offer.offerUrl())
                .location(offer.location())
                .workMode(offer.workMode())
                .salary(offer.salary())
                .company(offer.company())
                .skills(offer.skills())
                .source(offer.source())
                .fetchDate(offer.fetchDate())
                .build();
    }

    static Offer mapOfferDtoToOffer(OfferDto offerDto) {
        return Offer.builder()
                .title(offerDto.title())
                .offerUrl(offerDto.offerUrl())
                .location(offerDto.location())
                .workMode(offerDto.workMode())
                .salary(offerDto.salary())
                .company(offerDto.company())
                .skills(offerDto.skills())
                .source(offerDto.source())
                .fetchDate(offerDto.fetchDate())
                .build();
    }

    static OfferResponseDto mapOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .title(offer.title())
                .offerUrl(offer.offerUrl())
                .location(offer.location())
                .workMode(offer.workMode())
                .salary(offer.salary())
                .company(offer.company())
                .skills(offer.skills())
                .fetchDate(offer.fetchDate())
                .build();
    }
}
