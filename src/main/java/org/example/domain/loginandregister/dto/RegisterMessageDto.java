package org.example.domain.loginandregister.dto;


import lombok.Builder;

@Builder
public record RegisterMessageDto(Long userId, String message, boolean created) {
}
