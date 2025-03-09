package org.example.domain.akmf;

import java.util.Objects;

public class JobOffer {
    private String title;
    private String offerUrl;
    private String workMode;
    private String salary;
    private String location;
    private String detailsUrl;

    public JobOffer() {
    }

    public JobOffer(String title, String offerUrl, String workMode, String salary) {
        this.title = title;
        this.offerUrl = offerUrl;
        this.workMode = workMode;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public void setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOffer jobOffer = (JobOffer) o;
        return Objects.equals(offerUrl, jobOffer.offerUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerUrl);
    }
}