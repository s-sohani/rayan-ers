package com.rayanbourse.ers.service;

import java.util.Map;

public interface ExchangeRate {
    boolean isReady();
    double getExchangeRate(String currencyPair);
    Map<String, Integer> getCurrenciesNumberCall();

}
