package org.example.infrastructure.loginandregister.controler;

import lombok.AllArgsConstructor;
import org.example.domain.loginandregister.LoginAndRegisterFacade;
import org.example.domain.loginandregister.dto.RegisterMessageDto;
import org.example.domain.loginandregister.dto.RegisterUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegisterMessageDto> register(@RequestBody RegisterUserDto registerUserDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(registerUserDto.password());
        RegisterMessageDto registerResult = loginAndRegisterFacade.register(
                new RegisterUserDto(registerUserDto.username(), encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }

}
