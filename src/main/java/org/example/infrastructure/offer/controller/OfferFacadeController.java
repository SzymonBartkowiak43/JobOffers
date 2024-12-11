package org.example.infrastructure.offer.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.domain.offer.OfferFacade;
import org.example.domain.offer.dto.FindOfferDto;
import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.SavedMessageDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping("/offers")
    public ResponseEntity<SavedMessageDto> addNewOffers(@RequestBody @Valid OfferDto offerDto) {
        SavedMessageDto savedMessageDto = offerFacade.saveOffer(offerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessageDto);
    }
}
