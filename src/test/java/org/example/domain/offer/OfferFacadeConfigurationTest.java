package org.example.domain.offer;

public class OfferFacadeConfigurationTest {

    OfferFacade createForTest(OfferRepository offerRepository, OfferFeatchable offerFeatchable) {
        OfferService offerService = new OfferService(offerFeatchable,offerRepository);
        return new OfferFacade(offerRepository,offerService);
    }
}
