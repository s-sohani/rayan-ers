package com.rayanbourse.ers.controller;

import com.rayanbourse.ers.model.result.RestOutputModel;
import com.rayanbourse.ers.service.ExchangeRate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exchange rate controller
 */
@RestController
@RequestMapping("/api/exchange-rates")
@Log4j2
public class ExchangeRateController {
    @Autowired
    private ExchangeRate exchangeRate;

    @GetMapping("/rate")
    public ResponseEntity<RestOutputModel> getExchangeRate(@RequestParam String currencyPair) {
        try {
            return ResponseEntity.ok(RestOutputModel.builder().result(exchangeRate.getExchangeRate(currencyPair)).build());
        } catch (Exception e) {
            log.error("Error while getting exchange rate", e);
            return new ResponseEntity<>(RestOutputModel.builder().errorCode(5001)
                    .errorMessage("Error while getting exchange rate").build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/supported-currencies")
    public ResponseEntity<RestOutputModel> getSupportedCurrencies() {
        try {
            return ResponseEntity.ok(RestOutputModel.builder().result(exchangeRate.getCurrenciesNumberCall()).build());
        } catch (Exception e) {
            log.error("Error while getting supported currencies", e);
            return new ResponseEntity<>(RestOutputModel.builder().errorCode(5002)
                    .errorMessage("Error while getting supported currencies").build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/convert")
    public ResponseEntity<RestOutputModel> convertCurrency(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        try {
            return ResponseEntity.ok(RestOutputModel.builder().result(exchangeRate.convert(from, to, amount)).build());
        } catch (Exception e) {
            log.error("Error while convert currencies", e);
            return new ResponseEntity<>(RestOutputModel.builder().errorCode(5003)
                    .errorMessage("Error while convert currencies").build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chart-link")
    public ResponseEntity<RestOutputModel> getChartLink(@RequestParam String from, @RequestParam String to) {
        try {
            return ResponseEntity.ok(RestOutputModel.builder().result(exchangeRate.drawChart(from, to)).build());
        } catch (Exception e) {
            log.error("Error while getting chart link", e);
            return new ResponseEntity<>(RestOutputModel.builder().errorCode(5004)
                    .errorMessage("Error while getting chart link").build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
