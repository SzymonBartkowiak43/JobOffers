package org.example.infrastructure.akmf;


import org.example.domain.HtmlPages.service.BaseHtmlDownloader;
import org.springframework.stereotype.Service;



@Service
public class AkmfHtmlDownloader extends BaseHtmlDownloader {
    public AkmfHtmlDownloader() {
        super("https://www.akmf.pl/oferty-pracy");
    }
}
