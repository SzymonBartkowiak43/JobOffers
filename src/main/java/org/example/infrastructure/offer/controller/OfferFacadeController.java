package org.example.infrastructure.offer.controller;


import lombok.AllArgsConstructor;
import org.example.domain.offer.OfferFacade;
import org.example.domain.offer.dto.FindOfferDto;
import org.example.domain.offer.dto.OfferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OfferFacadeController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDto>> fetchOffers() {
        List<OfferDto> offerResponseDtos = offerFacade.findAllOffers();
        return ResponseEntity.ok(offerResponseDtos);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDto> fetchOfferById(@PathVariable String id) {
        OfferDto offer = offerFacade.findOfferById(new FindOfferDto(id));
        return ResponseEntity.ok(offer);
    }
}
