package org.example.infrastructure.theprotocol;


import org.example.domain.HtmlPages.model.TheProtocolJobOffer;
import org.example.domain.HtmlPages.ports.input.JobOfferProvider;
import org.example.domain.HtmlPages.response.JobOfferResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tpiOfferProvider")
public class TheProtocolOfferProvider implements JobOfferProvider {

    private final TheProtocolDownloader htmlDownloader;
    private final TheProtocolOfferParser parser;

    public TheProtocolOfferProvider(TheProtocolDownloader htmlDownloader, TheProtocolOfferParser parser) {
        this.htmlDownloader = htmlDownloader;
        this.parser = parser;
    }

    @Override
    public List<JobOfferResponse> fetchOffers() {
        String html = htmlDownloader.getHtml();
        return parser.parse(html).stream()
                .map(this::toResponse)
                .toList();
    }

    private JobOfferResponse toResponse(TheProtocolJobOffer offer) {
        return new JobOfferResponse(
                offer.getTitle(),
                offer.getOfferUrl(),
                offer.getLocation(),
                "",
                "",
                offer.getCompany(),
                offer.getSkills(),
                JobOfferResponse.SourceSystem.THE_PROTOCOL,
                offer.getFetchDate()
        );
    }
}
