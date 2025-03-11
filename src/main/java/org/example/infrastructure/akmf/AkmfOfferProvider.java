package org.example.infrastructure.akmf;

import org.example.domain.HtmlPages.model.AkmfJobOffer;
import org.example.domain.HtmlPages.ports.input.JobOfferProvider;
import org.example.domain.HtmlPages.response.JobOfferResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("akmfOfferProvider")
public class AkmfOfferProvider implements JobOfferProvider {

    private final AkmfHtmlDownloader htmlDownloader;
    private final AkmfOfferParser parser;

    public AkmfOfferProvider(AkmfHtmlDownloader htmlDownloader, AkmfOfferParser parser) {
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

    private JobOfferResponse toResponse(AkmfJobOffer offer) {
        return new JobOfferResponse(
                offer.getTitle(),
                offer.getOfferUrl(),
                offer.getLocation(),
                offer.getWorkMode(),
                offer.getSalary(),
                "Aplikacje Krytyczne",
                List.of(),
                JobOfferResponse.SourceSystem.AKMF,
                offer.getFetchDate()
        );
    }
}