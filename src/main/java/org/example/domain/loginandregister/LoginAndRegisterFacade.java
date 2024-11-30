package org.example.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.example.domain.loginandregister.dto.RegisterUserDto;
import org.example.domain.loginandregister.dto.RegisterMessageDto;
import org.example.domain.loginandregister.dto.UserDto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private static final String USER_NOT_FOUND = "Not found";
    private final UserValidator userValidator;
    private final UserRepository userRepository;


    public RegisterMessageDto register(RegisterUserDto registerUserDto) {
        String userName = registerUserDto.userName();
        String password = registerUserDto.password();

        List<ValidationResult> validationResults = userValidator.validate(userName, password);

        if(!validationResults.isEmpty()) {
            String resultMessage = userValidator.createResultMessage();
            return RegisterMessageDto.builder()
                    .message(resultMessage)
                    .created(false)
                    .build();
        }

        Optional<User> optionalUser = userRepository.findByUserName(userName);

        if(optionalUser.isPresent()) {
            return RegisterMessageDto.builder()
                    .created(false)
                    .message("User with this name already exists!")
                    .build();
        }

        User user = User.builder()
                .userName(userName)
                .password(password)
                .build();

        User savedUser = userRepository.save(user);

        return  RegisterMessageDto.builder()
                .userId(savedUser.id())
                .created(true)
                .message("success")
                .build();
    }

    public UserDto findByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return UserDto.builder()
                .userId(user.id())
                .message("success")
                .build();
    }
}
