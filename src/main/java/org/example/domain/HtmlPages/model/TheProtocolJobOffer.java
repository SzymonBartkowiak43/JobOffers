package org.example.domain.HtmlPages.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TheProtocolJobOffer extends BaseJobOffer {
    private String company;
    private List<String> skills;
}