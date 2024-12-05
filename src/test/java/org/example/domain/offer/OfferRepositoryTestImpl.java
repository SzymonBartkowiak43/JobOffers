package org.example.domain.offer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class OfferRepositoryTestImpl implements OfferRepository  {

    Map<String, Offer> offerDatabase = new ConcurrentHashMap<>();

    @Override
    public <S extends Offer> S save(S entity) {
        if (offerDatabase.values().stream().anyMatch(offer -> offer.offerUrl().equals(entity.offerUrl()))) {
            throw new OfferWithThisUriAlreadyExists(entity.offerUrl());
        }
        UUID id = UUID.randomUUID();
        Offer offer = new Offer(
                id.toString(),
                entity.title(),
                entity.company(),
                entity.salary(),
                entity.offerUrl()
        );
        offerDatabase.put(id.toString(), offer);
        return (S) offer;
    }

    public List<Offer> findAll() {
        return offerDatabase.values().stream().toList();
    }

    @Override
    public Optional<Offer> findById(String id) {
        try {
            Offer offer = offerDatabase.get(id);
            return Optional.of(offer);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .toList();
    }
//    @Override
//    public List<Offer> getAllOffer() {
//        return offerDatabase.values().stream().toList();
//    }

//    @Override
//    public Optional<Offer> getOfferByOfferUrl(String url) {
//        return Optional.empty();
//    }
//
//    public Optional<Offer> getOfferByOfferId(String offerId) {
//        return Optional.ofNullable(offerDatabase.get(offerId));
//    }
//
//    @Override
//    public List<Offer> saveAll(List<Offer> list) {
//        return null;
//    }

//    @Override
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

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }





    @Override
    public boolean existsById(String s) {
        return false;
    }


    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Offer> findOfferByOfferUrl(String url) {
        return Optional.empty();
    }
}
