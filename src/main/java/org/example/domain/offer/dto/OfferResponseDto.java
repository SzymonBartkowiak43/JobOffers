package org.example.domain.offer.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OfferResponseDto(     String offerId,
                                    String title,
                                    String offerUrl,
                                    String location,
                                    String workMode,
                                    String salary,
                                    String company,
                                    List<String> skills,
                                    LocalDateTime fetchDate) {
}
