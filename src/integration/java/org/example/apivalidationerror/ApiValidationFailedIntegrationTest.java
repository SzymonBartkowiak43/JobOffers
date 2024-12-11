package org.example.apivalidationerror;

import org.example.BaseIntegrationTest;
import org.example.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {


    @Test
    @WithMockUser
    public void should_reurn_400_bad_request_and_validation_message_empty_and_null() throws Exception {
        //given && when
        ResultActions performPostOffers = mockMvc.perform(post("/offers")
                .content("""
                        {
                            "title": "Junior Java Developer",
                            "company": "",
                            "salary": "7 000 – 9 000 PLN"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON +";charset=UTF-8"));

        //then
        String json = performPostOffers.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "company must not be empty",
                "offerUrl must not be empty",
                "offerUrl must not be null"
        );
    }
}
