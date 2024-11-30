package org.example.domain.loginandregister;

enum ValidationResult {

    SHORT_NAME("Name is too short"),
    SHORT_PASSWORD("Your password is to short");

    final String info;

    ValidationResult(String info) {
        this.info = info;
    }
}
