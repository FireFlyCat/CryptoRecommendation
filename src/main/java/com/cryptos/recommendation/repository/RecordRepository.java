package com.cryptos.recommendation.repository;

import com.cryptos.recommendation.model.db.RecordEntity;

import java.util.List;


public interface RecordRepository {


    public void saveRecords(List<RecordEntity> entities);

}
