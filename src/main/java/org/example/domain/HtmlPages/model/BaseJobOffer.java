package org.example.domain.HtmlPages.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseJobOffer {
    private String title;
    private String offerUrl;
    private String detailsUrl;
    private String location;
    private LocalDateTime fetchDate;
}