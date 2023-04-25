package com.cryptos.recommendation.model.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecordEntity(
        String symbol,
        LocalDateTime timestamp,
        BigDecimal price
) {
}
