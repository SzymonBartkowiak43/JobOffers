package org.example.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Document
public record Offer(
        @Id String offerId,
        String title,
        @Indexed(unique = true) String offerUrl,
        String location,
        String workMode,
        String salary,
        String company,
        List<String> skills,
        SourceSystem source,
        LocalDateTime fetchDate
) {
}

