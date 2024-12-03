package org.example.domain.offer;

import lombok.Builder;

@Builder
record Offer(String offerId, String title, String company, String salary, String offerUrl) {
}
