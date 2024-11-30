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
    OfferFeatchable offerFetcher = new OfferFeatcherTestImpl();
    OfferFacade offerFacade = new OfferFacadeConfigurationTest().createForTest(offerRepository, offerFetcher);


    @Test
    void should_save_6_offer_when_repository_has_0_in_the_database() {
        //given
        //when
        List<OfferResponseDto> offerResponseDtos = offerFacade.fetchAllOffersAndSaveAllNotExsists();
        //then
        assertThat(offerRepository.getAllOffer().size()).isEqualTo(6);
    }

    @Test
    void should_save_4_offer_when_repository_has_2_offer_with_the_same_urls() {
        //given
        Offer offer = new Offer("1","aaa", "junior", "abba", "7000-9000", "1");
        Offer offer1 = new Offer("2","aaa", "junior", "abba", "7000-9000", "2");
        offerRepository.save(offer);
        offerRepository.save(offer1);
        assertThat(offerRepository.getAllOffer().size()).isEqualTo(2);
        //when
        List<OfferResponseDto> offerResponseDtos = offerFacade.fetchAllOffersAndSaveAllNotExsists();
        //then
        assertThat(offerRepository.getAllOffer().size()).isEqualTo(6);
    }



    @Test
    void should_save_offer_add_return_message_success() {
        //given
        OfferDto offerDto = OfferDto.builder()
                .position("Junior")
                .title("Junior Java Developer")
                .companyName("BlueSoft Sp. z.o.o")
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
                .position("Junior")
                .title("Junior Java Developer")
                .companyName("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com")
                .build();
        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);
        SavedMessageDto savedOfferDto2 =  offerFacade.saveOffer(offerDto);
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
                .position("Junior")
                .title("Junior Java Developer")
                .companyName("BlueSoft Sp. z.o.o")
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
                .position("Junior")
                .title("Junior Java Developer")
                .companyName("BlueSoft Sp. z.o.o")
                .salary("7 000 - 9 000")
                .offerUrl("https://notfulljobs.com")
                .build();
        SavedMessageDto savedOfferDto =  offerFacade.saveOffer(offerDto);
        //when
        //then
        assertThrows(OfferNotFoundException.class, () -> offerFacade.findOfferById(new FindOfferDto("2")), "Offer not found" ) ;
    }

}