package com.cryptos.recommendation.importer;

import com.cryptos.recommendation.model.domain.InitialRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RecordsImporter {

    public RecordsImporter() {
    }

    /**
     * Method returns list of files containing initial records
     */
    public File[] getListOfInitialFiles() {
        try {
            File initialFiles = new File(this.getClass().getClassLoader().getResource("initialfiles").toURI());
            return initialFiles.listFiles();
        } catch (Exception e) {
            log.error("Errors when load initial files");
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns file content as list of converted records.
     *
     * @implNote ideally instead of readAllLines should be BufferReader to avoid possible issues with big data
     * but skipped for better performance. this also fixes the issue with 1st and last line
     */
    public List<InitialRecord> readFile(File file) {
        try {
            return Files.readAllLines(file.toPath())
                    .stream()
                    .filter(line -> !line.trim().equals("timestamp,symbol,price") &&
                            !line.trim().equals(""))
                    .map(this::toInitialRecord)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Errors reading file");
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts csv file line into the record. TimeZone depending
     */
    private InitialRecord toInitialRecord(String line) {
        String[] split = line.split(",");
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(split[0])),
                TimeZone.getDefault().toZoneId()); //TODO move zone to config
        BigDecimal price = new BigDecimal(split[2]);
        return new InitialRecord(split[1], timestamp, price);
    }
}
