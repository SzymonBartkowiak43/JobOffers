package org.example.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferFacadeErrorResponse(String message, HttpStatus status) {
}
