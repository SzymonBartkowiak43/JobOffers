package org.example.domain.loginandregister;

import lombok.Builder;

@Builder
record User(Long id, String userName, String password) {
}
