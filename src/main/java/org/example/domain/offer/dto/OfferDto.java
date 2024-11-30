package org.example.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(String title, String position, String companyName, String salary, String offerUrl) {
}
