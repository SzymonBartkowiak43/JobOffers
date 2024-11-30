package org.example.domain.offer;

import lombok.Builder;

@Builder
record Offer(String offerId, String title, String position, String companyName, String salary, String offerUrl) {
}
