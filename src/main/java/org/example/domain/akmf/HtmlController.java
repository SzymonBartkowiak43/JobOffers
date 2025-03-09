package org.example.domain.akmf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HtmlController {

    private final HtmlDownloader htmlDownloader;

    public HtmlController(HtmlDownloader htmlDownloader) {
        this.htmlDownloader = htmlDownloader;
    }

    @GetMapping("/download")
    public String download() {
        return htmlDownloader.downloadHtml();
    }
}