package com.cryptos.recommendation.model.api;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record BestCryptoRequest(
        @NotNull
        LocalDate date
) {
}
