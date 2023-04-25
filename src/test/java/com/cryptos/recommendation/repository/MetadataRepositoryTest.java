package com.cryptos.recommendation.repository;

import com.cryptos.recommendation.model.db.CurrencyMetadataMonthlyEntity;
import com.cryptos.recommendation.model.db.RecordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
public class MetadataRepositoryTest {

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private RecordRepository recordRepository;

    @BeforeEach
    public void setUp() {
        recordRepository.saveRecords(Arrays.asList(
                new RecordEntity("TEST", LocalDateTime.of(2020, 1, 1, 1, 1), new BigDecimal("10")),
                new RecordEntity("TEST", LocalDateTime.of(2020, 1, 1, 2, 1), new BigDecimal("20")),
                new RecordEntity("TEST", LocalDateTime.of(2020, 1, 2, 1, 1), new BigDecimal("15")),
                new RecordEntity("TEST2", LocalDateTime.of(2020, 1, 1, 1, 1), new BigDecimal("15")),
                new RecordEntity("TEST2", LocalDateTime.of(2020, 1, 1, 1, 1), new BigDecimal("50"))
        ));
    }

    @Test
    public void getCurrencyWithHighestNormalizationByDate_exceptedHighestNormalizationSymbol() {
        String currency = metadataRepository.getCurrencyWithHighestNormalizationByDate(LocalDate.of(2020, 1, 1));

        assertThat(currency).isEqualTo("TEST2");
    }

    @Test
    public void getCurrencyWithHighestNormalizationByDate_expectedOnly2Results() {
        List<String> stringList = metadataRepository.getAllCurrencySymbolsSortedByNormalization();

        assertThat(stringList.size()).isEqualTo(2);
        assertThat(stringList.get(0)).isEqualTo("TEST2");
        assertThat(stringList.get(1)).isEqualTo("TEST");
    }

    @Test
    public void getCurrencyDetails() {
        CurrencyMetadataMonthlyEntity details = metadataRepository.getCurrencyDetails("TEST");

        assertThat(details.maxPrice()).isEqualByComparingTo(new BigDecimal("20"));
        assertThat(details.minPrice()).isEqualByComparingTo(new BigDecimal("10"));
        assertThat(details.oldestPrice()).isEqualByComparingTo(new BigDecimal("10"));
        assertThat(details.newestPrice()).isEqualByComparingTo(new BigDecimal("15"));
        assertThat(details.normalization()).isEqualByComparingTo(new BigDecimal("1"));
        assertThat(details.symbol()).isEqualTo("TEST");
    }
}
