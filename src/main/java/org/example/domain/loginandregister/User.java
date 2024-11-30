package org.example.domain.loginandregister;

import lombok.Builder;

@Builder
record User(String id, String userName, String password) {
}
