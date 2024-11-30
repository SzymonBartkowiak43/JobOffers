package org.example.domain.loginandregister;

public class LoginAndRegisterConfiguration {

    public LoginAndRegisterFacade createForTest(UserRepository userRepository) {
        UserValidator userValidator = new UserValidator();
        return new LoginAndRegisterFacade(userValidator,userRepository);
    }
}
