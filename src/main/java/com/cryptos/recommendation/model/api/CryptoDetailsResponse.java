package com.cryptos.recommendation.model.api;

import java.math.BigDecimal;

public record CryptoDetailsResponse(
        String symbol,
        BigDecimal maxPrice,
        BigDecimal minPrice,
        BigDecimal oldestPrice,
        BigDecimal newestPrice,
        BigDecimal normalization
) {
}
