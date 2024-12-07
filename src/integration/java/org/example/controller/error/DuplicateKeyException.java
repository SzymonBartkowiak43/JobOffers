package org.example.controller.error;

import org.example.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DuplicateKeyException extends BaseIntegrationTest {


    @Test
    public void should_return_409_conflict_when_added_second_offer_with_this_same_url() throws Exception {
        //step 1:
        //given && when
        ResultActions performPostOffers1 = mockMvc.perform(post("/offers")
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


        //step 2:
        //given && when
        ResultActions performPostOffers2 = mockMvc.perform(post("/offers")
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
}
