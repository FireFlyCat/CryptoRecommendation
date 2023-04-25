package com.cryptos.recommendation.repository;

import com.cryptos.recommendation.model.db.RecordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.cryptos.recommendation.repository.utility.DbUtility.*;

@Component
@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepository {

    public static final String INSERT_RECORD = "INSERT INTO records (symbol, timestamp, price) VALUES (:symbol, :timestamp, :price)";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void saveRecords(List<RecordEntity> entities) {
        int[] ints = jdbcTemplate.batchUpdate(
                INSERT_RECORD,
                entities.stream().map(this::getParametersMap).toList().toArray(new Map[0]));
    }

    private Map<String, Object> getParametersMap(RecordEntity record) {
        return Map.of(SYMBOL, record.symbol(), TIMESTAMP, record.timestamp(), PRICE, record.price());
    }
}
