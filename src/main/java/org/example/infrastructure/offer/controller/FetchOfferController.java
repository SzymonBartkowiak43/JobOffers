package org.example.infrastructure.offer.controller;


import lombok.AllArgsConstructor;
import org.example.domain.offer.OfferFacade;
import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class FetchOfferController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDto>> fetchOffers() {
        List<OfferDto> offerResponseDtos = offerFacade.findAllOffers();
        return ResponseEntity.ok(offerResponseDtos);
    }
}
