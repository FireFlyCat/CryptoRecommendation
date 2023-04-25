package com.cryptos.recommendation.repository;

import com.cryptos.recommendation.exception.CryptoCurrencyNotFoundException;
import com.cryptos.recommendation.mapper.CurrencyMetadataMonthlyMapper;
import com.cryptos.recommendation.model.db.CurrencyMetadataMonthlyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cryptos.recommendation.repository.utility.DbUtility.DATE_TO_SEARCH;
import static com.cryptos.recommendation.repository.utility.DbUtility.SYMBOL;

@Component
@RequiredArgsConstructor
public class MetadataRepositoryImpl implements MetadataRepository {

    private static final String SELECT_ALL_CURRENCY_SYMBOLS = """
            SELECT symbol
            FROM currency_metadata_months
            ORDER BY normalization desc
            """;
    private static final String SELECT_BEST_SYMBOL_FOR_SPECIFIC_DAY = """
            SELECT TOP 1 symbol
            FROM currency_metadata_days
            WHERE datediff(DAY, :dateToSearch, day_timestamp) = 0
            ORDER BY normalization desc
            """;
    private static final String SELECT_CRYPTO_DETAILS = """
            SELECT TOP 1 *
            FROM currency_metadata_months
            WHERE symbol = :symbol
            ORDER BY month_timestamp desc
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final CurrencyMetadataMonthlyMapper currencyMetadataMonthlyMapper;

    public List<String> getAllCurrencySymbolsSortedByNormalization() {
        return jdbcTemplate.queryForList(SELECT_ALL_CURRENCY_SYMBOLS, Map.of())
                .stream().map(entry -> entry.get(SYMBOL).toString())
                .collect(Collectors.toList());
    }

    public String getCurrencyWithHighestNormalizationByDate(LocalDate date) {
        List<String> stringList = jdbcTemplate.queryForList(
                SELECT_BEST_SYMBOL_FOR_SPECIFIC_DAY,
                Map.of(DATE_TO_SEARCH, date), String.class);
        if (stringList.size() == 0) {
            throw new CryptoCurrencyNotFoundException("No Crypto Currency found for the requested day");
        }
        return stringList.get(0);
    }

    public CurrencyMetadataMonthlyEntity getCurrencyDetails(String cryptoName) {
        List<CurrencyMetadataMonthlyEntity> symbolList = jdbcTemplate
                .query(SELECT_CRYPTO_DETAILS,
                        Map.of(SYMBOL, cryptoName), currencyMetadataMonthlyMapper);
        if (symbolList.size() == 0) {
            throw new CryptoCurrencyNotFoundException("CryptoCurrency with specified name is not supported");
        }
        return symbolList.get(0);
    }
}
