package com.cryptos.recommendation.repository;

import com.cryptos.recommendation.model.db.CurrencyMetadataMonthlyEntity;

import java.time.LocalDate;
import java.util.List;


public interface MetadataRepository {


    public List<String> getAllCurrencySymbolsSortedByNormalization();

    public String getCurrencyWithHighestNormalizationByDate(LocalDate date);

    public CurrencyMetadataMonthlyEntity getCurrencyDetails(String cryptoName);
}
