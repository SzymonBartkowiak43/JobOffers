package org.example.infrastructure.justjoin;



import org.example.domain.HtmlPages.model.JustJoinJobOffer;
import org.example.domain.HtmlPages.ports.output.OfferParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JustJoinOfferParser implements OfferParser<JustJoinJobOffer> {

    private static final String JOB_CARD_SELECTOR = "div.css-1jbajow";
    private static final String TITLE_SELECTOR = "h3.css-1gehlh0";
    private static final String LINK_SELECTOR = "a[href]";
    private static final String COMPANY_SELECTOR = "div.css-1kb0cuq > span";
    private static final String LOCATION_SELECTOR = "span.css-1o4wo1x";
    private static final String SKILLS_CONTAINER_SELECTOR = "div.css-vzlxkq";
    private static final String SKILL_ITEM_SELECTOR = "div[class^='skill-tag-'] div.css-jikuwi";


    @Override
    public List<JustJoinJobOffer> parse(String html) {
        Document doc = Jsoup.parse(html);
        return doc.select(JOB_CARD_SELECTOR)
                .stream()
                .map(this::parseJobElement)
                .toList();
    }

    private JustJoinJobOffer parseJobElement(Element jobElement) {
        JustJoinJobOffer offer = new JustJoinJobOffer();

        offer.setTitle(extractText(jobElement, TITLE_SELECTOR));
        extractUrls(jobElement).ifPresent(urls -> {
            offer.setOfferUrl(urls[0]);
            offer.setDetailsUrl(urls[1]);
        });
        offer.setCompany(extractText(jobElement, COMPANY_SELECTOR));
        offer.setLocation(extractLocation(jobElement));
        offer.setSkills(extractSkills(jobElement));
        offer.setPublishedDate(extractPublishDate(jobElement));
        offer.setFetchDate(LocalDateTime.now());

        return offer;
    }

    private String extractText(Element context, String selector) {
        return Optional.ofNullable(context.selectFirst(selector))
                .map(Element::text)
                .orElse("");
    }

    private Optional<String[]> extractUrls(Element context) {
        return Optional.ofNullable(context.selectFirst(LINK_SELECTOR))
                .map(element -> new String[]{
                        "https://justjoin.it" + element.attr("href"),
                        "https://justjoin.it" + element.attr("href")
                });
    }

    private String extractLocation(Element context) {
        return Optional.ofNullable(context.selectFirst(LOCATION_SELECTOR))
                .map(element -> element.text().split(",")[0])
                .orElse("");
    }

    private List<String> extractSkills(Element context) {
        return Optional.ofNullable(context.selectFirst(SKILLS_CONTAINER_SELECTOR))
                .map(container -> container.select(SKILL_ITEM_SELECTOR))
                .map(elements -> elements.eachText())
                .orElseGet(ArrayList::new);
    }

    private LocalDateTime extractPublishDate(Element context) {
        return Optional.ofNullable(context.selectFirst("time"))
                .map(element -> element.attr("datetime"))
                .map(Instant::parse)
                .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
                .orElse(null);
    }
}