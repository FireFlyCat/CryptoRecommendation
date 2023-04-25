package com.cryptos.recommendation.exception;

public class CryptoCurrencyNotFoundException extends RuntimeException {

    public CryptoCurrencyNotFoundException(String message) {
        super(message);
    }
}
