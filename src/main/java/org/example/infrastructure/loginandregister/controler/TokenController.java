package org.example.infrastructure.loginandregister.controler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.infrastructure.loginandregister.controler.dto.JwtResponseDto;
import org.example.infrastructure.loginandregister.controler.dto.TokenRequestDto;
import org.example.infrastructure.security.JwtAuthenticatorFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authericateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequest) {
        final JwtResponseDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }


}
