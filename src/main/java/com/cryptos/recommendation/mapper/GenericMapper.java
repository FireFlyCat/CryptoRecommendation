package com.cryptos.recommendation.mapper;

import com.cryptos.recommendation.model.api.CryptoDetailsResponse;
import com.cryptos.recommendation.model.db.CurrencyMetadataMonthlyEntity;
import com.cryptos.recommendation.model.db.RecordEntity;
import com.cryptos.recommendation.model.domain.CryptoDetails;
import com.cryptos.recommendation.model.domain.InitialRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenericMapper {
    CryptoDetailsResponse cryptoDetailsToCryptoDetailsResponse(CryptoDetails details);

    CryptoDetails currencyMetadataMonthlyEntityToCryptoDetails(CurrencyMetadataMonthlyEntity entity);

    RecordEntity initialRecordToRecordEntity(InitialRecord record);
}
