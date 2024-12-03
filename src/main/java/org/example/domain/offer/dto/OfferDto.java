package org.example.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(String title, String company, String salary, String offerUrl) {
}
