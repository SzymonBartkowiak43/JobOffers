package org.example.infrastructure.justjoin;


import org.example.domain.HtmlPages.model.JustJoinJobOffer;
import org.example.domain.HtmlPages.ports.input.JobOfferProvider;
import org.example.domain.HtmlPages.response.JobOfferResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("justJoinOfferProvider")
public class JustJoinOfferProvider implements JobOfferProvider {

    private final JustJoinHtmlDownloader justJoinHtmlDownloader;
    private final JustJoinOfferParser justJoinOfferParser;

    public JustJoinOfferProvider(JustJoinHtmlDownloader justJoinHtmlDownloader, JustJoinOfferParser justJoinOfferParser) {
        this.justJoinHtmlDownloader = justJoinHtmlDownloader;
        this.justJoinOfferParser = justJoinOfferParser;
    }

    @Override
    public List<JobOfferResponse> fetchOffers() {
        String html = justJoinHtmlDownloader.getHtml();
        return justJoinOfferParser.parse(html).stream()
                .map(this::toResponse)
                .toList();
    }

    private JobOfferResponse toResponse(JustJoinJobOffer offer) {
        return new JobOfferResponse(
                offer.getTitle(),
                offer.getOfferUrl(),
                offer.getLocation(),
                "",
                "",
                offer.getCompany(),
                offer.getSkills(),
                JobOfferResponse.SourceSystem.JUST_JOIN,
                offer.getFetchDate()
        );
    }
}

