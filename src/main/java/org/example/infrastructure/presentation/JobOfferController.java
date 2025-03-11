package org.example.infrastructure.presentation;

import org.example.domain.HtmlPages.ports.input.JobOfferProvider;
import org.example.domain.HtmlPages.response.JobOfferResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class JobOfferController {

    private final JobOfferProvider akmfOfferProvider;
    private final JobOfferProvider tpiOfferProvider;
    private final JobOfferProvider justJoinOfferProvider;

    public JobOfferController(
            @Qualifier("akmfOfferProvider") JobOfferProvider akmfOfferProvider,
            @Qualifier("tpiOfferProvider") JobOfferProvider tpiOfferProvider,
            @Qualifier("justJoinOfferProvider") JobOfferProvider justJoinOfferProvider) {
        this.akmfOfferProvider = akmfOfferProvider;
        this.tpiOfferProvider = tpiOfferProvider;
        this.justJoinOfferProvider = justJoinOfferProvider;
    }

    @GetMapping("/akmf")
    public ResponseEntity<List<JobOfferResponse>> getAkmfOffers() {
        return ResponseEntity.ok(akmfOfferProvider.fetchOffers());
    }

    @GetMapping("/tpi")
    public ResponseEntity<List<JobOfferResponse>> getTpiOffers() {
        return ResponseEntity.ok(tpiOfferProvider.fetchOffers());
    }

    @GetMapping("/justjoin")
    public ResponseEntity<List<JobOfferResponse>> getJustJoinOffers() {
        return ResponseEntity.ok(justJoinOfferProvider.fetchOffers());
    }
}