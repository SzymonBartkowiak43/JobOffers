package org.example.domain.offer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record OfferDto(
        @NotNull(message = "{title.not.null}")
        @NotEmpty(message = "{title.not.empty}")
        String title,
        @NotNull(message = "{company.not.null}")
        @NotEmpty(message = "{company.not.empty}")
        String company,
        @NotNull(message = "{salary.not.null}")
        @NotEmpty(message = "{salary.not.empty}")
        String salary,
        @NotNull(message = "{offerUrl.not.null}")
        @NotEmpty(message = "{offerUrl.not.empty}")
        String offerUrl) implements Serializable {
}
