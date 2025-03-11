package org.example.domain.HtmlPages.response;

import java.time.LocalDateTime;
import java.util.List;

public record JobOfferResponse(
        String title,
        String offerUrl,
        String location,
        String workMode,
        String salary,
        String company,
        List<String> skills,
        SourceSystem source,
        LocalDateTime fetchDate
) {
    public enum SourceSystem {
        AKMF, THE_PROTOCOL, JUST_JOIN
    }
}
