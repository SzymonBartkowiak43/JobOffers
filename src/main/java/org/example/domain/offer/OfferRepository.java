package org.example.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    Optional<Offer> save(Offer offer);

    List<Offer> getAllOffer();

    Optional<Offer> getOfferById(String s);

    List<Offer> saveAll(List<Offer> list);
}
