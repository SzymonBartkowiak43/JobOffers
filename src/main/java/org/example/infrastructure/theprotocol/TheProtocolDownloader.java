package org.example.infrastructure.theprotocol;

import org.example.domain.HtmlPages.service.BaseHtmlDownloader;
import org.springframework.stereotype.Service;



@Service
public class TheProtocolDownloader extends BaseHtmlDownloader {
    public TheProtocolDownloader() {
        super("https://theprotocol.it/filtry/java;t/junior;p/bialystok,warszawa;wp/hybrydowa,zdalna;rw");
    }
}
