package com.cryptos.recommendation.mapper;

import com.cryptos.recommendation.model.db.CurrencyMetadataMonthlyEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CurrencyMetadataMonthlyMapper implements RowMapper<CurrencyMetadataMonthlyEntity> {
    @Override
    public CurrencyMetadataMonthlyEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CurrencyMetadataMonthlyEntity(
                rs.getNString("symbol"),
                rs.getTimestamp("month_timestamp").toLocalDateTime().toLocalDate(),
                rs.getBigDecimal("max_price"),
                rs.getBigDecimal("min_price"),
                rs.getBigDecimal("old_price"),
                rs.getBigDecimal("new_price"),
                rs.getBigDecimal("normalization")
        );
    }
}
