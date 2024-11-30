package org.example.domain.offer;

public class OfferFacadeConfigurationTest {

    OfferFacade createForTest(OfferRepository offerRepository, OfferFetchable offerFeatchable) {
        OfferService offerService = new OfferService(offerFeatchable,offerRepository);
        return new OfferFacade(offerRepository,offerService);
    }
}
