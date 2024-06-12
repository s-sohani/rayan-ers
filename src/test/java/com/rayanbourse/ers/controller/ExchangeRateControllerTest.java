package com.rayanbourse.ers.controller;

import com.rayanbourse.ers.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
class ExchangeRateControllerTest extends TestTemplate {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getExchangeRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange-rates/rate")
                        .accept(MediaType.APPLICATION_JSON)
                .param("currencyPair", "USD/EUR"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":0.9289363678588016,\"errorMessage\":null,\"errorCode\":null}"));
    }

    @Test
    void getSupportedCurrencies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange-rates/supported-currencies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":{\"CHF\":0,\"MXN\":0,\"ZAR\":0,\"INR\":0,\"CNY\":0,\"THB\":0,\"AUD\":0,\"ILS\":0,\"KRW\":0,\"JPY\":0,\"PLN\":0,\"GBP\":0,\"IDR\":0,\"HUF\":0,\"PHP\":0,\"TRY\":0,\"ISK\":0,\"HKD\":0,\"EUR\":0,\"DKK\":0,\"USD\":0,\"CAD\":0,\"MYR\":0,\"BGN\":0,\"NOK\":0,\"RON\":0,\"SGD\":0,\"CZK\":0,\"SEK\":0,\"NZD\":0,\"BRL\":0},\"errorMessage\":null,\"errorCode\":null}"));
    }

    @Test
    void convertCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange-rates/convert")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "USD")
                        .param("to", "EUR")
                        .param("amount", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":1.8578727357176033,\"errorMessage\":null,\"errorCode\":null}"));
    }

    @Test
    void getChartLink() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange-rates/chart-link")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "USD")
                        .param("to", "EUR"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":\"https://www.xe.com/currencycharts/?from=USD&to=EUR\",\"errorMessage\":null,\"errorCode\":null}"));
    }
}