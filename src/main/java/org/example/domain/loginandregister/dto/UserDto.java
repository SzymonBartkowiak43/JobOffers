package org.example.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(String userId, String username, String password, String message) {
}
