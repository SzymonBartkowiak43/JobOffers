package org.example.infrastructure.offer.scheduler;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.domain.offer.OfferFacade;
import org.example.domain.offer.dto.OfferResponseDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class FetchAllOfferScheduler {

    private final OfferFacade offerFacade;
    private static final String START = "Started offers fetching {}";
    private static final String STOP = "Stopped offers fetching {}";
    private static final String ADD_NEW_OFFERS_MESSAGE = "Added new {} offers";
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${http.offers.scheduler.request.delay}")
    public  List<OfferResponseDto> FetchOfferDto() {
        log.info(START, dateFormat.format(new Date()));
        List<OfferResponseDto> addedOffers = offerFacade.fetchAllOffersAndSaveAllNotExists();
        log.info(ADD_NEW_OFFERS_MESSAGE, addedOffers.size());
        log.info(STOP, dateFormat.format(new Date()));
        return addedOffers;
    }

}
