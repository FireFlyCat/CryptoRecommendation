package com.cryptos.recommendation.service;

import com.cryptos.recommendation.mapper.GenericMapper;
import com.cryptos.recommendation.model.domain.CryptoDetails;
import com.cryptos.recommendation.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralInfoServiceImpl implements GeneralInfoService {

    private final MetadataRepository metadataRepository;

    private final GenericMapper genericMapper;

    public List<String> getAllCryptos() {
        return metadataRepository.getAllCurrencySymbolsSortedByNormalization();
    }

    public String getBestCrypto(LocalDate date) {
        return metadataRepository.getCurrencyWithHighestNormalizationByDate(date);
    }

    @Override
    public CryptoDetails getCrypoDetails(String cryptoName) {
        return genericMapper.currencyMetadataMonthlyEntityToCryptoDetails(metadataRepository.getCurrencyDetails(cryptoName));
    }

}
