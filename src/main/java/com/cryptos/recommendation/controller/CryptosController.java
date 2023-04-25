package com.cryptos.recommendation.controller;

import com.cryptos.recommendation.mapper.GenericMapper;
import com.cryptos.recommendation.model.api.BasicCryptoResponse;
import com.cryptos.recommendation.model.api.BestCryptoRequest;
import com.cryptos.recommendation.model.api.CryptoDetailsResponse;
import com.cryptos.recommendation.repository.RecordRepository;
import com.cryptos.recommendation.service.GeneralInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cryptos")
@RequiredArgsConstructor
@Validated
public class CryptosController {

    private final GeneralInfoService generalInfoService;

    private final GenericMapper mapper;

    private final RecordRepository recordRepository;

    @Operation(
            summary = "Get list of all crypto currencies supported by the system",
            description = "Get list of all crypto currencies supported by the system ordered by normalization"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of currencies",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("")
    public ResponseEntity<List<BasicCryptoResponse>> getAllCryptos() {
        return ResponseEntity.ok(generalInfoService.getAllCryptos()
                .stream().map(BasicCryptoResponse::new)
                .collect(Collectors.toList()));
    }

    @Operation(
            summary = "Get the best crypto currency for the specific date",
            description = "Get the best (by normalization) crypto currency for the specific date"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Best crypto currency for the specific date",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error response with error message",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/best")
    public ResponseEntity<BasicCryptoResponse> getBestCrypto(@Valid @ModelAttribute BestCryptoRequest request) {
        return ResponseEntity.ok(new BasicCryptoResponse(generalInfoService.getBestCrypto(request.date())));
    }

    @Operation(
            summary = "Get crypto currency details by currency name",
            description = "Get details of crypto currency including max,min,oldest and newest prices for the last month"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Crypto currency details",
                    useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error response with error message",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/crypto/{cryptoName}")
    public ResponseEntity<CryptoDetailsResponse> getCryptoDetails(@PathVariable("cryptoName") String cryptoName) {
        return ResponseEntity.ok(mapper.cryptoDetailsToCryptoDetailsResponse(generalInfoService.getCrypoDetails(cryptoName.toUpperCase())));
    }
}
