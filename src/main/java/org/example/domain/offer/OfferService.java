package org.example.domain.offer;

import lombok.AllArgsConstructor;
import org.example.domain.offer.dto.OfferDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class OfferService {

    private final OfferFetchable offerFetcher;
    private final OfferRepository offerRepository;


    List<Offer> fetchAllOffer() {
        List<OfferDto> offerFetcherDtos = offerFetcher.fetchOffers();


        List<String> offerUrl = offerRepository.findAll().stream()
                .map(Offer::offerUrl)
                .toList();

        List<OfferDto> offerDtos = OfferFilter.filterOffers(offerFetcherDtos, offerUrl);

        List<Offer> list = offerDtos.stream()
                .map(OfferMapper::mapOfferDtoToOffer)
                .toList();

        List<Offer> offers = offerRepository.saveAll(list);

        return offers;
    }
}
