package org.example.infrastructure.offer.controller.error;


import lombok.extern.log4j.Log4j2;
import org.example.domain.offer.OfferNotFoundException;
import org.example.domain.offer.OfferWithThisUriAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class OfferFacadeControllerErrorHandler {


    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OfferFacadeErrorResponse offerNotFoundException(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new OfferFacadeErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferWithThisUriAlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public OfferFacadeErrorResponse offerDuplicate(OfferWithThisUriAlreadyExists exception) {
        String message = exception.getMessage();
        log.error(message);
        return new OfferFacadeErrorResponse(message, HttpStatus.CONFLICT);
    }
}
