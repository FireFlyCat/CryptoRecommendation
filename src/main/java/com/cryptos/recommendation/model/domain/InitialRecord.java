package com.cryptos.recommendation.model.domain;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InitialRecord(
        String symbol,
        LocalDateTime timestamp,
        BigDecimal price

) {

}
