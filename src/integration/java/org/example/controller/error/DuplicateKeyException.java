package org.example.controller.error;

import org.example.BaseIntegrationTest;
import org.example.domain.loginandregister.dto.RegisterMessageDto;
import org.example.infrastructure.loginandregister.controler.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DuplicateKeyException extends BaseIntegrationTest {


    @Test
    public void should_return_409_conflict_when_added_second_offer_with_this_same_url() throws Exception {
        //step 1: someUser was registered with somePassword
        String token = createUserAndGetToken();
        //step 2:
        //given && when
        ResultActions performPostOffers1 = mockMvc.perform(post("/offers")
                .header("Authorization", "Bearer " + token)
                .content("""
                        {
                            "title": "Junior Java Developer",
                            "company": "BlueSoft Sp. z o.o.",
                            "salary": "7 000 – 9 000 PLN",
                            "offerUrl": "uniqueLink"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON +";charset=UTF-8"));
        //then
        performPostOffers1.andExpect(status().isCreated());


        //step 3:
        //given && when
        ResultActions performPostOffers2 = mockMvc.perform(post("/offers")
                .header("Authorization", "Bearer " + token)
                .content("""
                        {
                            "title": "Junior Java Developer",
                            "company": "BlueSoft Sp. z o.o.",
                            "salary": "7 000 – 9 000 PLN",
                            "offerUrl": "uniqueLink"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON +";charset=UTF-8"));
        //then
        performPostOffers2.andExpect(status().isConflict());
    }

    private String createUserAndGetToken() throws Exception {

        ResultActions registerAction = mockMvc.perform(post("/register")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult registerActionResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerActionResultJson = registerActionResult.getResponse().getContentAsString();
        RegisterMessageDto registrationResultDto = objectMapper.readValue(registerActionResultJson, RegisterMessageDto.class);
        assertAll(
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.userId()).isNotNull()
        );


        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // given && when
        ResultActions successLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultSuccess = successLoginRequest.andExpect(status().isOk()).andReturn();
        String SuccessJson = mvcResultSuccess.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(SuccessJson, JwtResponseDto.class);
        String token = jwtResponse.token();
        assertAll(
                () -> assertThat(jwtResponse.username()).isEqualTo("someUser"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );

        return token;

    }
}
