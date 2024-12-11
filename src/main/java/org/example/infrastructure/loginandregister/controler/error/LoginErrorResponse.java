package org.example.infrastructure.loginandregister.controler.error;

import org.springframework.http.HttpStatus;

public record LoginErrorResponse(String message, HttpStatus status) {
}
