package org.example.domain.akmf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class JobOfferParser {

    public static void main(String[] args) throws IOException {
        String htmlContent = Files.readString(Path.of("src/main/resources/akmf.json"));
        List<JobOffer> offers = parseJobOffers(htmlContent);

        System.out.println(offers.get(0).getTitle());
        System.out.println(offers.get(0).getOfferUrl());
    }

    public static List<JobOffer> parseJobOffers(String html) {
        List<JobOffer> jobOffers = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements offerElements = document.select("div.JobOffers_jobBox__WHKHK");

        for (Element offer : offerElements) {
            JobOffer jobOffer = new JobOffer();

            Element titleElement = offer.selectFirst("div.JobOffers_jobBoxContainer_title__MCWmi a");
            if (titleElement != null) {
                jobOffer.setTitle(titleElement.attr("title"));
                jobOffer.setOfferUrl("www.akmf.pl/" +titleElement.attr("href"));
            }

            Element locationType = offer.selectFirst(".JobOffers_jobBoxContainer_typeText__Mxf4E");
            if (locationType != null) {
                jobOffer.setLocation(locationType.text());
            }

            Element salary = offer.selectFirst(".JobOffers_jobBoxContainer_type__B0ZYp");
            if (salary != null) {
                jobOffer.setSalary(salary.text());
            }

            Element detailsLink = offer.selectFirst(".JobOffers_jobBoxContainer_linkBox__WYlPJ a");
            if (detailsLink != null) {
                jobOffer.setDetailsUrl(detailsLink.attr("href"));
            }

            jobOffers.add(jobOffer);
        }

        return jobOffers;
    }
}