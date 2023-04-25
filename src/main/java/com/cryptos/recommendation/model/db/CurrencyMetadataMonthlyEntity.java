package com.cryptos.recommendation.model.db;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CurrencyMetadataMonthlyEntity(
        String symbol,
        LocalDate timestamp,
        BigDecimal maxPrice,
        BigDecimal minPrice,
        BigDecimal oldestPrice,
        BigDecimal newestPrice,
        BigDecimal normalization

) {
}
