package org.example.domain.loginandregister;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
class UserValidator {


    private static final int MIN_LENGTH_OF_USER_NAME = 3;
    private static final int MIN_LENGTH_OF_PASSWORD = 3;

    private List<ValidationResult> errors;

    List<ValidationResult> validate(String userName, String password) {
        errors = new LinkedList<>();
        validateUserName(userName);
        validatePassword(password);

        return errors;
    }

    String createResultMessage() {
        return this.errors
                .stream()
                .map(validationResult -> validationResult.info)
                .collect(Collectors.joining(","));
    }

    private void validateUserName(String userName) {
        if(userName.length() <= MIN_LENGTH_OF_USER_NAME) {
            errors.add(ValidationResult.SHORT_NAME);
        }
    }

    private void validatePassword(String password) {
        if(password.length() <= MIN_LENGTH_OF_PASSWORD) {
            errors.add(ValidationResult.SHORT_PASSWORD);
        }
    }
}
