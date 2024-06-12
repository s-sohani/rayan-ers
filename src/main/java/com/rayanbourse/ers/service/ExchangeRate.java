package com.rayanbourse.ers.service;

import java.util.Map;

public interface ExchangeRate {
    boolean isReady();
    double getExchangeRate(String currencyPair);
    Map<String, Integer> getCurrenciesNumberCall();
    double convert(String from, String to, double amount);
    String drawChart(String from, String to);

}
