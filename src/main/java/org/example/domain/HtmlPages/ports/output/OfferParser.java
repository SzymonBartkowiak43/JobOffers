package org.example.domain.HtmlPages.ports.output;

import java.util.List;

public interface OfferParser<T> {
    List<T> parse(String html);
}