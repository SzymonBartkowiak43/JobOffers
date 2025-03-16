package org.example.domain.offer.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.domain.offer.SourceSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OfferDto(
        String title,
        @NotNull(message = "{offerUrl.not.null}")
        @NotEmpty(message = "{offerUrl.not.empty}")
        String offerUrl,
        String location,
        String workMode,
        String salary,
        String company,
        List<String> skills,
        SourceSystem source,
        LocalDateTime fetchDate
)
         implements Serializable {
}
