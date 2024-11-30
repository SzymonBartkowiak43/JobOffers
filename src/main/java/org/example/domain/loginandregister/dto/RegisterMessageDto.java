package org.example.domain.loginandregister.dto;


import lombok.Builder;

@Builder
public record RegisterMessageDto(String userId, String message, boolean created) {
}
