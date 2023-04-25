package com.cryptos.recommendation.controller;

import com.cryptos.recommendation.exception.CryptoCurrencyNotFoundException;
import com.cryptos.recommendation.model.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(value
            = {CryptoCurrencyNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> cryptoCurrencyNotFoundHandler(CryptoCurrencyNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ErrorMessage(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value
            = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> genericRuntimeExceptionHandler(RuntimeException exception) {
        return ResponseEntity.internalServerError().body(
                new ErrorMessage(
                        "Unexpected error found. Please try again. If error persists again, please call support team."));
    }
}
