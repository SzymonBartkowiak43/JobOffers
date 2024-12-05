package org.example.domain.offer;

import org.example.domain.offer.dto.FindOfferDto;
import org.example.domain.offer.dto.OfferDto;
import org.example.domain.offer.dto.OfferResponseDto;
import org.example.domain.offer.dto.SavedMessageDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfferFacadeTest {

    OfferRepository offerRepository = new OfferRepositoryTestImpl();
    OfferFetchable offerFetcher = new OfferFetcherTestImpl();
    OfferFacade offerFacade = new OfferFacadeConfigurationTest().createForTest(offerRepository, offerFetcher);


    @Test
    void should_save_6_offer_when_repository_has_0_in_the_database() {
        //given
        //when
        List<OfferResponseDto> offerResponseDtos = offerFacade.fetchAllOffersAndSaveAllNotExists();
        //then
        assertThat(offerRepository.findAll().size()).isEqualTo(6);
    }

    @Test
    void should_save_4_offer_when_repository_has_2_offer_with_the_same_urls() {
        //given
        Offer offer = new Offer("1","aaa",  "abba", "7000-9000", "1");
        Offer offer1 = new Offer("2","aaa",  "abba", "7000-9000", "2");
        offerRepository.save(offer);
        offerRepository.save(offer1);
        assertThat(offerRepository.findAll().size()).isEqualTo(2);
        //when
        List<OfferResponseDto> offerResponseDtos = offerFacade.fetchAllOffersAndSaveAllNotExists();
        //then
        assertThat(offerRepository.findAll().size()).isEqualTo(6);
    }



    @Test
    void should_save_offer_add_return_message_success() {
        //given
        OfferDto offerDto = OfferDto.builder()
                .title("Junior Java Developer")
                .company("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com")
                .build();

        //when
        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);

        //then
        assertTrue(savedOfferDto.created());
        assertThat(savedOfferDto.message()).isEqualTo("success");
    }

    @Test
    void should_return_all_offer_from_database() {
        //given
        OfferDto offerDto = OfferDto.builder()
                .title("Junior Java Developer")
                .company("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com/1")
                .build();

        OfferDto offerDto2 = OfferDto.builder()
                .title("Junior Java Developer")
                .company("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com/2")
                .build();

        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);
        SavedMessageDto savedOfferDto2 =  offerFacade.saveOffer(offerDto2);
        //when
        List<OfferDto> allOffers = offerFacade.findAllOffers();

        //then
        assertTrue(savedOfferDto.created());
        assertTrue(savedOfferDto2.created());
        assertThat(allOffers.size()).isEqualTo(2);
    }

    @Test
    void should_return_offer_by_id() {
        //given
        OfferDto offerDto = OfferDto.builder()
                .title("Junior Java Developer")
                .company("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com")
                .build();
        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);
        String offerId = savedOfferDto.offerId();
        //when
        OfferDto offerById = offerFacade.findOfferById(new FindOfferDto(offerId));
        //then
        assertThat(offerById.title()).isEqualTo("Junior Java Developer");
    }

    @Test
    public void should_throws_exception_offer_not_exists() {
        //given
        OfferDto offerDto = OfferDto.builder()
                .title("Junior Java Developer")
                .company("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com")
                .build();
        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);
        //when
        //then
        assertThrows(OfferNotFoundException.class, () -> offerFacade.findOfferById(new FindOfferDto("2")), "Offer not found" ) ;
    }

    @Test
    public void should_throw_exception_offer_with_this_uri_already_exists(){
        //given
        OfferDto offerDto = OfferDto.builder()
                .title("Junior Java Developer")
                .company("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com")
                .build();
        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);
        //when
        //then
        assertThrows(OfferWithThisUriAlreadyExists.class, () -> offerFacade.saveOffer(offerDto), "Duplicated uri!");
    }




}