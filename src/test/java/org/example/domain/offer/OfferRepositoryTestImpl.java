package org.example.domain.offer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OfferRepositoryTestImpl implements OfferRepository  {

    Map<String, Offer> offerDatabase = new ConcurrentHashMap<>();

    @Override
    public Optional<Offer> save(Offer offer) {
        UUID id = UUID.randomUUID();
        Offer savedOffer = new Offer(
                id.toString(),
                offer.title(),
                offer.company(),
                offer.salary(),
                offer.offerUrl()
        );
        boolean offerWithThisUriExisit = offerDatabase.values()
                .stream()
                .anyMatch(o -> o.offerUrl().equals(offer.offerUrl()));

        if(offerWithThisUriExisit) {
            throw new OfferWithThisUriAlreadyExists("Duplicated uri!");
        }

        offerDatabase.put(savedOffer.offerId(), savedOffer);
        return Optional.of(savedOffer);
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
                    offer.company(),
                    offer.salary(),
                    offer.offerUrl()
            );
            offerDatabase.put(savedOffer.offerId(), offer);
            returnedList.add(savedOffer);
        }
        return returnedList;
    }
}
