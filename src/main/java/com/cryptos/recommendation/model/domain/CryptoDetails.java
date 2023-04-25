package com.cryptos.recommendation.model.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CryptoDetails (
        String symbol,
        LocalDate timestamp,
        BigDecimal maxPrice,
        BigDecimal minPrice,
        BigDecimal oldestPrice,
        BigDecimal newestPrice,
        BigDecimal normalization
){
}
