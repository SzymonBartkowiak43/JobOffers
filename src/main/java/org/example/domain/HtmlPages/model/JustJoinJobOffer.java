package org.example.domain.HtmlPages.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class JustJoinJobOffer extends BaseJobOffer {
    private String company;
    private List<String> skills;
    private LocalDateTime publishedDate;
}
