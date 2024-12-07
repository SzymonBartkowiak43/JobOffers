package org.example.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.example.BaseIntegrationTest;
import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;
import org.example.domain.offer.dto.SavedMessageDto;
import org.example.infrastructure.offer.scheduler.FetchAllOfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class TypicalScenarioWantToSeeOffersIntegrationTest extends BaseIntegrationTest {

    @Autowired
    FetchAllOfferScheduler fetchAllOfferScheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        //step 1: there are 0 offers in external HTTP server (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)
        //given
            wireMockServer.stubFor(WireMock.get("/offers")
                    .willReturn(WireMock.aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", "application/json")
                            .withBody(bodyWith0OffersJson())));
            //when
            //then


        //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given
        List<OfferResponseDto> offerResponseDtos = fetchAllOfferScheduler.FetchOfferAndSaveIfNotExists();

        assertThat(offerResponseDtos).isEmpty();
//        await()
//                .atMost(Duration.ofSeconds(20))
//                .pollInterval(Duration.ofSeconds(1))
//                .until(() -> {
//                    offerFetchable.fetchOffers();
//                   return true;
//                });
        //when
        //then



        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //step 7: user made GET /offers  and system returned OK(200) with 0 offers

        // given && when
        ResultActions performGetAllOffers = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then

        MvcResult mvcResult = performGetAllOffers.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<OfferDto> offers = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertThat(offers).isEmpty();



        //step 8: there are 2 new offers in external HTTP server
        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000


        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given && when
        ResultActions performGetOffer= mockMvc.perform(get("/offers/9999"));
        //then
        performGetOffer.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "message": "Offer with id 9999 not found",
                            "status": "NOT_FOUND"
                        }
                        """.trim()
                ));


        //step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //step 13: there are 2 new offers in external HTTP server
        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        //step 16: user made /POST /Offers with body and system returned 201 created with saved offer
        //given && when
        ResultActions performPostOffers = mockMvc.perform(post("/offers")
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
        MvcResult resultFromPostOffer = performPostOffers.andExpect(status().isCreated()).andReturn();
        String postedJson = resultFromPostOffer.getResponse().getContentAsString();
        SavedMessageDto savedMessageDto = objectMapper.readValue(postedJson, SavedMessageDto.class);
        String offerId = savedMessageDto.offerId();

        assertAll(
                () -> assertThat(offerId).isNotNull(),
                () -> assertTrue(savedMessageDto.created())
        );



        //step 17: user made GET /offers and system returned OK(200) with 1 offer
        // given && when
        ResultActions performGetOfferExisit= mockMvc.perform(get("/offers/" + offerId));
        //then
        String findOfferJson = performGetOfferExisit.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferDto parsedJsonToOffer =  objectMapper.readValue(findOfferJson, OfferDto.class);

        assertAll(
                () -> assertThat(parsedJsonToOffer.title()).isEqualTo("Junior Java Developer"),
                () -> assertThat(parsedJsonToOffer.company()).isEqualTo("BlueSoft Sp. z o.o.")
        );


    }

    private String bodyWith4OffersJson() {
        return """
                [
                    {
                        "title": "Junior Java Developer",
                        "company": "BlueSoft Sp. z o.o.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                    },
                    {
                        "title": "Java (CMS) Developer",
                        "company": "Efigence SA",
                        "salary": "16 000 – 18 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "Sollers Consulting",
                        "salary": "7 500 – 11 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
                    },
                    {
                        "title": "Junior Full Stack Developer",
                        "company": "Vertabelo S.A.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-full-stack-developer-vertabelo-remote-k7m9xpnm"
                    }
                ]
                """.trim();
    }

    private String bodyWith0OffersJson() {
        return "";
    }
}
