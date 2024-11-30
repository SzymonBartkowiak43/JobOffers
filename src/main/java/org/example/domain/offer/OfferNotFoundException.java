package org.example.domain.offer;

public class OfferNotFoundException extends RuntimeException {

    OfferNotFoundException(String message) {
        super(message);
    }
}
