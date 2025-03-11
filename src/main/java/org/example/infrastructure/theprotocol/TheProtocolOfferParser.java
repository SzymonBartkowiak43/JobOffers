package org.example.infrastructure.theprotocol;

import org.example.domain.HtmlPages.model.TheProtocolJobOffer;
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
public class TheProtocolOfferParser implements OfferParser<TheProtocolJobOffer> {

    public List<TheProtocolJobOffer> parse(String html) {
        List<TheProtocolJobOffer> offers = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        Elements jobElements = doc.select("a[data-test='list-item-offer']");

        for (Element jobElement : jobElements) {
            TheProtocolJobOffer offer = new TheProtocolJobOffer();

            Element titleElement = jobElement.selectFirst("h2[data-test='text-jobTitle']");
            if (titleElement != null) {
                offer.setTitle(titleElement.text());
            }

            String relativeUrl = jobElement.attr("href");
            offer.setOfferUrl("https://theprotocol.it/" + relativeUrl);
            offer.setDetailsUrl(offer.getOfferUrl());

            Element companyElement = jobElement.selectFirst("div[data-test='text-employerName']");
            if (companyElement != null) {
                offer.setCompany(companyElement.text());
            }

            if (relativeUrl != null) {
                String[] urlParts = relativeUrl.split("-");
                if (urlParts.length > 2) {
                    String city = urlParts[2];
                    offer.setLocation(city);
                }
            }

            Elements skillElements = jobElement.select("div[data-test='chip-expectedTechnology'] span.l1sjc53z");
            List<String> skills = new ArrayList<>();
            for (Element skillElement : skillElements) {
                skills.add(skillElement.text());
            }

            offer.setSkills(skills);
            offer.setFetchDate(LocalDateTime.now());

            offers.add(offer);
        }
        return offers;
    }
}
