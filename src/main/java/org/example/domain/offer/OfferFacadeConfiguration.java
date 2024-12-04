package org.example.domain.offer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    OfferFacade offerFacade(OfferFetchable offerFetchable) {

        OfferRepository repository = new OfferRepository() {
            @Override
            public Optional<Offer> save(Offer offer) {
                return Optional.empty();
            }

            @Override
            public List<Offer> getAllOffer() {
                return null;
            }

            @Override
            public Optional<Offer> getOfferById(String s) {
                return Optional.empty();
            }

            @Override
            public List<Offer> saveAll(List<Offer> list) {
                return null;
            }
        };
        OfferService offerService = new OfferService(offerFetchable, repository);
        return new OfferFacade(repository,offerService);
    }

}
