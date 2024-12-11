package org.example.domain.loginandregister;

import org.example.domain.loginandregister.dto.RegisterUserDto;
import org.example.domain.loginandregister.dto.RegisterMessageDto;
import org.example.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class LoginAndRegisterFacadeTest {

    UserRepository userRepository = new UserRepositoryTestImpl();
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterConfiguration().createForTest(userRepository);

    @Test
    public void should_return_success_and_create_new_user() {
        //given
        String userName = "Kamil123";
        String password = "password";
        RegisterUserDto registerUserDto = new RegisterUserDto(userName, password);
        //when
        RegisterMessageDto response = loginAndRegisterFacade.register(registerUserDto);
        //then
        assertThat(response.message()).isEqualTo("success");
        assertTrue(response.created());
    }

    @Test
    public void should_return_failed_user_name_is_too_short() {
        //given
        String userName = "Ka";
        String password = "password";
        RegisterUserDto registerUserDto = new RegisterUserDto(userName, password);
        //when
        RegisterMessageDto response = loginAndRegisterFacade.register(registerUserDto);
        //then
        assertThat(response.message()).isEqualTo("Name is too short");
        assertFalse(response.created());
    }

    @Test
    public void should_return_failed_password_is_too_short() {
        //given
        String userName = "Kamil123";
        String password = "pa";
        RegisterUserDto registerUserDto = new RegisterUserDto(userName, password);
        //when
        RegisterMessageDto response = loginAndRegisterFacade.register(registerUserDto);
        //then
        assertThat(response.message()).isEqualTo("Your password is to short");
        assertFalse(response.created());
    }

    @Test
    public void should_return_failed_user_with_this_name_already_exists() {
        //given
        String userName = "Kamil123";
        String password = "password";
        RegisterUserDto registerUserDto = new RegisterUserDto(userName, password);
        //when
        RegisterMessageDto response = loginAndRegisterFacade.register(registerUserDto);
        RegisterMessageDto response2 = loginAndRegisterFacade.register(registerUserDto);
        //then
        assertThat(response.message()).isEqualTo("success");
        assertThat(response2.message()).isEqualTo("User with this name already exists!");
        assertTrue(response.created());
        assertFalse(response2.created());
    }


    @Test
    public void should_return_success_and_user_hash() {
        //given
        String userName = "Kamil123";
        String password = "password";
        RegisterUserDto registerUserDto = new RegisterUserDto(userName, password);

        loginAndRegisterFacade.register(registerUserDto);
        //when
        UserDto response = loginAndRegisterFacade.findByUserName(userName);
        //then
        assertThat(response.message()).isEqualTo("success");
    }

    @Test
    public void should_throws_exception_user_not_exists() {
        //given
        String userName = "Kamil123";
        //when
        //then
        assertThrows(BadCredentialsException.class, () -> loginAndRegisterFacade.findByUserName(userName), "Not Found" ) ;
    }

}