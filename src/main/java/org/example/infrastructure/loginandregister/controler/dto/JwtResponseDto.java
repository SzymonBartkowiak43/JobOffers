package org.example.infrastructure.loginandregister.controler.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String username,
        String token
) {

}
