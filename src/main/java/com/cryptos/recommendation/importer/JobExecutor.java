package com.cryptos.recommendation.importer;

import com.cryptos.recommendation.mapper.GenericMapper;
import com.cryptos.recommendation.model.db.RecordEntity;
import com.cryptos.recommendation.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnProperty(value = "crypto.importer.enabled", havingValue = "true")
@Component
@Slf4j
public class JobExecutor {

    private final RecordsImporter recordsImporter;

    private final RecordRepository recordRepository;


    private final GenericMapper genericMapper;

    public JobExecutor(RecordsImporter recordsImporter, RecordRepository recordRepository, GenericMapper genericMapper) {
        this.recordsImporter = recordsImporter;
        this.recordRepository = recordRepository;

        this.genericMapper = genericMapper;

        execute();
    }

    /**
     * Simple Job Engine =) . In case of Big Data use Spring Batch should with chunks for records processing
     */
    private void execute() {
        log.info("Start Importing Job");

        log.info("Import file records");
        List<RecordEntity> entityList = Arrays
                .stream(recordsImporter.getListOfInitialFiles())
                .flatMap(file -> recordsImporter.readFile(file).stream())
                .filter(record -> record.price() != null && record.timestamp() != null)
                .map(genericMapper::initialRecordToRecordEntity)
                .collect(Collectors.toList());
        log.info("Save records");
        recordRepository.saveRecords(entityList);

        log.info("End Importing Job");
    }
}
