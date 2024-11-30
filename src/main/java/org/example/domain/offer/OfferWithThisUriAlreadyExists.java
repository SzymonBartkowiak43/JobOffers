package org.example.domain.offer;

public class OfferWithThisUriAlreadyExists extends RuntimeException{
    OfferWithThisUriAlreadyExists(String message) {
        super(message);
    }
}
