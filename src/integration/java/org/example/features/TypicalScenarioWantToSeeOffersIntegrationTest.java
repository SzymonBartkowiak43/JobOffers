package org.example.features;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.util.List;
import java.util.regex.Pattern;

import org.example.BaseIntegrationTest;
import org.example.SampleJobOffer;
import org.example.domain.loginandregister.dto.RegisterMessageDto;
import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;
import org.example.domain.offer.dto.SavedMessageDto;
import org.example.infrastructure.loginandregister.controler.dto.JwtResponseDto;
import org.example.infrastructure.offer.scheduler.FetchAllOfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOffer {

    @Autowired
    FetchAllOfferScheduler fetchAllOfferScheduler;


    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        //step 1: there are 0 offers in external HTTP server ()
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers/all")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody(bodyWith0OffersJson())));


        //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given
        List<OfferResponseDto> newOffers =  fetchAllOfferScheduler.FetchOfferAndSaveIfNotExists();

        //when && then
        assertThat(newOffers).isEmpty();


        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // given && when
        ResultActions failedLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(
                        """
                                {
                                "message": "Bad Credentials",
                                "status": "UNAUTHORIZED"
                                }
                                """.trim()
                ));


        //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        ResultActions failedGetOffersRequest = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedGetOffersRequest.andExpect(status().isUnauthorized());


        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(201)
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


        //step 7: user made GET /offers  and system returned OK(200) with 0 offers
        // given && when
        ResultActions performGetAllOffersWhenEqual0 = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult = performGetAllOffersWhenEqual0.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<OfferDto> offers = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertThat(offers).isEmpty();


        //step 8: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers/all")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody(bodyWith2OffersJson())));

        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        // given
        List<OfferResponseDto> offerSecondResponse = fetchAllOfferScheduler.FetchOfferAndSaveIfNotExists();

        // when
        OfferResponseDto offer1 = offerSecondResponse.get(0);
        OfferResponseDto offer2 = offerSecondResponse.get(1);
        //then
        assertThat(offerSecondResponse.size()).isEqualTo(2);


        //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        // given
        ResultActions performGet2Offers = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        // when
        String offersJson = performGet2Offers.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<OfferDto> twoOffers = objectMapper.readValue(offersJson, new TypeReference<>() {
        });

        //then
        assertAll(
                () -> assertThat(twoOffers.size()).isEqualTo(2)
        );


        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given && when
        ResultActions performGetOfferNotExist = mockMvc.perform(get("/offers/9999")
                .header("Authorization", "Bearer " + token));
        //then
        performGetOfferNotExist.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "message": "Offer with id 9999 not found",
                            "status": "NOT_FOUND"
                        }
                        """.trim()
                ));


        //step 12: user made GET /offers/offer1.id and system returned OK(200) with offer
        //given && when
        ResultActions performOffer1 = mockMvc.perform(get("/offers/" + offer1.offerId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));
        //then
        String json1Offer = performOffer1
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferDto offer1Dto = objectMapper.readValue(json1Offer, OfferDto.class);

        assertAll(
                () -> assertThat(offer1Dto.offerUrl()).isEqualTo(offer1.offerUrl()),
                () -> assertThat(offer1Dto.title()).isEqualTo(offer1.title())
        );

        //step 13: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers/all")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody(bodyWith4OffersJson())));
        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database

        // given
        List<OfferResponseDto> offerThirdResponse = fetchAllOfferScheduler.FetchOfferAndSaveIfNotExists();

        // when && then
        assertThat(offerThirdResponse.size()).isEqualTo(2);

        //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        //given
        ResultActions performGet4Offers = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        String offers4Json = performGet4Offers.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<OfferDto> fourOffers = objectMapper.readValue(offers4Json, new TypeReference<>() {
        });

        //then
        assertAll(
                () -> assertThat(fourOffers.size()).isEqualTo(4)
        );

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
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"));

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
        ResultActions performGetOfferExisit = mockMvc.perform(get("/offers/" + offerId)
                .header("Authorization", "Bearer " + token));
        //then
        String findOfferJson = performGetOfferExisit.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferDto parsedJsonToOffer = objectMapper.readValue(findOfferJson, OfferDto.class);

        assertAll(
                () -> assertThat(parsedJsonToOffer.title()).isEqualTo("Junior Java Developer"),
                () -> assertThat(parsedJsonToOffer.company()).isEqualTo("BlueSoft Sp. z o.o.")
        );


    }


}
