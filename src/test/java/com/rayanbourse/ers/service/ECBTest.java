package com.rayanbourse.ers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ECBServiceTest {

    @Autowired
    public ECBService ecbService;

    @BeforeEach
    public void setUp() {
        await().until(() -> ecbService.isReady());
    }

    @Test
    public void testFillCubeData() {
        assertTrue(ecbService.isReady());
    }

    @Test
    public void testGetExchangeRate_validInput_getRate() {
        var result = ecbService.getExchangeRate("USD/EUR");
        assertEquals(0.9319664492078286, result);

        result = ecbService.getExchangeRate("HUF/EUR");
        assertEquals(0.0025340192078655956, result);

        result = ecbService.getExchangeRate("HUF/USD");
        assertEquals(0.002719002610039784, result);
    }

    @Test
    public void testGetExchangeRate_inValidInput_throwException() {
        assertThrows(NullPointerException.class, () -> ecbService.getExchangeRate("invalid/EUR"));
    }

    @Test
    public void testGetCurrenciesNumberCall(){
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
        assertEquals(1.073, ecbService.convert("EUR", "USD", 1.0));
    }
}