package org.example.infrastructure.akmf;

import org.example.domain.HtmlPages.model.AkmfJobOffer;
import org.example.domain.HtmlPages.ports.output.OfferParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AkmfOfferParser implements OfferParser<AkmfJobOffer> {
    public List<AkmfJobOffer> parse(String html) {

        List<AkmfJobOffer> offers = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        Elements jobElements = doc.select("div.JobOffers_jobBox__WHKHK");

        for (Element jobElement : jobElements) {
            AkmfJobOffer offer = new AkmfJobOffer();

            Element titleElement = jobElement.selectFirst("div.JobOffers_jobBoxContainer_title__MCWmi a");
            if (titleElement != null) {
                offer.setTitle(titleElement.text());
                offer.setOfferUrl("https://www.akmf.pl/" + titleElement.attr("href"));
            }

            Element workModeElement = jobElement.selectFirst("div.JobOffers_jobBoxContainer_typeText__Mxf4E");
            if (workModeElement != null) {
                offer.setWorkMode(workModeElement.text());
            }

            Element salaryElement = jobElement.selectFirst("div.JobOffers_jobBoxContainer_type__B0ZYp");
            if (salaryElement != null) {
                offer.setSalary(salaryElement.text());
            }

            Element detailsElement = jobElement.selectFirst("div.JobOffers_jobBoxContainer_linkBox__WYlPJ a");
            if (detailsElement != null) {
                offer.setDetailsUrl(detailsElement.attr("href"));
            }

            offer.setFetchDate(LocalDateTime.now());

            offers.add(offer);
        }

        return offers;
    }
}