package org.example.domain.offer;

import lombok.AllArgsConstructor;
import org.example.domain.offer.dto.FindOfferDto;
import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;
import org.example.domain.offer.dto.SavedMessageDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {

    private static final String DUPLICATED_URI = "Duplicated uri!";

    private final OfferRepository offerRepository;
    private final OfferService offerService;


    public List<OfferResponseDto> fetchAllOffersAndSaveAllNotExists() {
        List<Offer> offers = offerService.fetchAllOffer();

        return offers.stream()
                .map(OfferMapper::mapOfferToOfferResponseDto)
                .collect(Collectors.toList());
    }

    @Cacheable("jobOffers")
    public List<OfferDto> findAllOffers() {
        List<Offer> allOffer = offerRepository.findAll();

        return allOffer.stream()
                .map(OfferMapper::mapOfferToOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto findOfferById(FindOfferDto findOfferDto) {
        Offer offer = offerRepository.findById(findOfferDto.OfferId())
                .orElseThrow(() -> new OfferNotFoundException("Offer with id " + findOfferDto.OfferId() + " not found"));

        return OfferMapper.mapOfferToOfferDto(offer);
    }

    public SavedMessageDto saveOffer(OfferDto offerDto) {
        Offer offer = OfferMapper.mapOfferDtoToOffer(offerDto);

        Optional<Offer> offerByOfferUrl = offerRepository.findOfferByOfferUrl(offer.offerUrl());

        if(offerByOfferUrl.isPresent()) {
            throw new OfferWithThisUriAlreadyExists(DUPLICATED_URI);
        }

        Offer savedOffer = offerRepository.save(offer);

        return SavedMessageDto.builder()
                .message("success")
                .offerId(savedOffer.offerId())
                .created(true)
                .build();
    }


}
