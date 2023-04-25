package com.cryptos.recommendation.service;

import com.cryptos.recommendation.model.domain.CryptoDetails;

import java.time.LocalDate;
import java.util.List;

public interface GeneralInfoService {

    List<String> getAllCryptos();

    String getBestCrypto(LocalDate date);

    CryptoDetails getCrypoDetails(String cryptoName);
}
