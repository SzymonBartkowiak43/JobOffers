package org.example.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(Long userId, String message) {
}
