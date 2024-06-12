package com.rayanbourse.ers.service;

import com.rayanbourse.ers.TestTemplate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ECBServiceTest extends TestTemplate {
    @Test
    public void testFillCubeData() {
        assertTrue(ecbService.isReady());
    }

    @Test
    public void testGetExchangeRate_validInput_getRate() {
        var result = ecbService.getExchangeRate("USD/EUR");
        assertEquals(0.9289363678588016, result);

        result = ecbService.getExchangeRate("HUF/EUR");
        assertEquals(0.002529852256628213, result);

        result = ecbService.getExchangeRate("HUF/USD");
        assertEquals(0.0027233859542602715, result);
    }

    @Test
    public void testGetExchangeRate_inValidInput_throwException() {
        assertThrows(NullPointerException.class, () -> ecbService.getExchangeRate("invalid/EUR"));
    }

    @Test
    public void testGetCurrenciesNumberCall() {
        assertEquals(0, ecbService.getCurrenciesNumberCall().get("HUF"));
        assertEquals(0, ecbService.getCurrenciesNumberCall().get("USD"));
        assertEquals(0, ecbService.getCurrenciesNumberCall().get("EUR"));
        ecbService.getExchangeRate("HUF/USD");
        assertEquals(1, ecbService.getCurrenciesNumberCall().get("HUF"));
        assertEquals(1, ecbService.getCurrenciesNumberCall().get("USD"));
        assertEquals(0, ecbService.getCurrenciesNumberCall().get("EUR"));
    }

    @Test
    public void testConvert() {
        assertEquals(1.0765, ecbService.convert("EUR", "USD", 1.0));
    }

    @Test
    public void testDrawChart() {
        var res = ecbService.drawChart("USD", "EUR");
        assertEquals("https://www.xe.com/currencycharts/?from=USD&to=EUR", res);
    }
}