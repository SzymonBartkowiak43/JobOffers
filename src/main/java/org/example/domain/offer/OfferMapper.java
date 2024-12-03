package org.example.domain.offer;

import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    static OfferDto mapOfferToOfferDto(Offer offer) {
        return OfferDto.builder()
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    static Offer mapOfferDtoToOffer(OfferDto offerDto) {
        return Offer.builder()
                .title(offerDto.title())
                .company(offerDto.company())
                .salary(offerDto.salary())
                .offerUrl(offerDto.offerUrl())
                .build();
    }

    static OfferResponseDto mapOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .offerId(offer.offerId())
                .title(offer.title())
                .companyName(offer.company())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }
}
