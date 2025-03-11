package org.example.infrastructure.justjoin;

import org.example.domain.HtmlPages.service.BaseHtmlDownloader;
import org.springframework.stereotype.Service;

@Service
public class JustJoinHtmlDownloader extends BaseHtmlDownloader {
    protected JustJoinHtmlDownloader() {
        super("https://justjoin.it/job-offers/all-locations/java?experience-level=junior&orderBy=DESC&sortBy=published");
    }
}
