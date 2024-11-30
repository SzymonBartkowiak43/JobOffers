package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    static OfferDto mapOfferToOfferDto(Offer offer) {
        return OfferDto.builder()
                .title(offer.title())
                .position(offer.position())
                .companyName(offer.companyName())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    static Offer mapOfferDtoToOffer(OfferDto offerDto) {
        return Offer.builder()
                .title(offerDto.title())
                .position(offerDto.position())
                .companyName(offerDto.companyName())
                .salary(offerDto.salary())
                .offerUrl(offerDto.offerUrl())
                .build();
    }

    static OfferResponseDto mapOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .offerId(offer.offerId())
                .title(offer.title())
                .position(offer.position())
                .companyName(offer.companyName())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }
}
