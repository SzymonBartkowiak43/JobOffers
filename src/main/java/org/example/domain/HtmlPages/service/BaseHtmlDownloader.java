package org.example.domain.HtmlPages.service;

import org.example.domain.HtmlPages.ports.output.HtmlDownloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;


public abstract class BaseHtmlDownloader implements HtmlDownloader {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36";
    protected final String websiteUrl;

    protected BaseHtmlDownloader(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Override
    public String getHtml() {
        try {
            trustAllCertificates();
            URLConnection connection = new URL(websiteUrl).openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Connection failed", e);
        }
    }
}