package com.cryptos.recommendation.controller;

import com.cryptos.recommendation.model.db.RecordEntity;
import com.cryptos.recommendation.repository.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CryptosControllerITest {
    @Autowired
    private MockMvc mockMvc;

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
    void getAllCryptos_expectedCurrencyInRightOrder() throws Exception {
        mockMvc.perform(get("/api/cryptos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("TEST2"))
                .andExpect(jsonPath("$[1].symbol").value("TEST"));
    }

    @Test
    void getBestCrypto_expectedBestCurrencyByNormalization() throws Exception {
        mockMvc.perform(get("/api/cryptos/best?date=2020-01-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("TEST2"));
    }

    @Test
    void getCryptoDetails_expectedCurrencyDetails() throws Exception {
        mockMvc.perform(get("/api/cryptos/crypto/test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("TEST"))
                .andExpect(jsonPath("$.oldestPrice").value(10))
                .andExpect(jsonPath("$.newestPrice").value(15))
                .andExpect(jsonPath("$.minPrice").value(10))
                .andExpect(jsonPath("$.maxPrice").value(20));
    }
}
