package org.example.domain.offer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository  {

    Map<String, Offer> offerDatabase = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer offer) {
        UUID id = UUID.randomUUID();
        Offer savedOffer = new Offer(
                id.toString(),
                offer.title(),
                offer.position(),
                offer.companyName(),
                offer.salary(),
                offer.offerUrl()
        );

        offerDatabase.put(savedOffer.offerId(), savedOffer);
        return savedOffer;
    }

    @Override
    public List<Offer> getAllOffer() {
        return offerDatabase.values().stream().toList();
    }

    @Override
    public Optional<Offer> getOfferById(String offerId) {
        return Optional.ofNullable(offerDatabase.get(offerId));
    }

    @Override
    public List<Offer> saveAll(List<Offer> list) {

        List<Offer> returnedList = new LinkedList<>();
        for(Offer offer: list) {
            UUID id = UUID.randomUUID();
            Offer savedOffer = new Offer(
                    id.toString(),
                    offer.title(),
                    offer.position(),
                    offer.companyName(),
                    offer.salary(),
                    offer.offerUrl()
            );
            offerDatabase.put(savedOffer.offerId(), offer);
            returnedList.add(savedOffer);
        }
        return returnedList;
    }
}
