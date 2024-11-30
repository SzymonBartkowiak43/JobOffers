package org.example.domain.offer.dto;

import lombok.Builder;

@Builder
public record SavedMessageDto(String offerId, String message, boolean created) {
}
