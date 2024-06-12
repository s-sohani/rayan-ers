package com.rayanbourse.ers.service;

import com.rayanbourse.ers.model.ecb.CubeData;
import com.rayanbourse.ers.model.ecb.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * European Central Bank Rate
 */
@Service
@EnableScheduling
public class ECBService implements ExchangeRate {

    @Value("${ecb_url}")
    private String ecbUrl;
    @Autowired
    private RestTemplate restClient;
    private Map<String, CubeData> cubes;
    private Map<String, Integer> currenciesNumberToCall = new HashMap<>();

    @Scheduled(fixedRate = 60000)
    private void fillCubeData() {
        var ecbXmlData = restClient.getForObject(ecbUrl, String.class);
        var envelop = convertECBXmlToObject(ecbXmlData);
        cubes = envelop.getCube().getCubes().stream().findAny().orElseThrow().getCubeData().stream()
                .collect(Collectors.toMap(CubeData::getCurrency, Function.identity()));
        cubes.put("EUR", new CubeData("EUR", 1.0));
        cubes.keySet().forEach(currency -> currenciesNumberToCall.put(currency, currenciesNumberToCall.getOrDefault(currency, 0 )));
    }

    private Envelope convertECBXmlToObject(String ecbXmlData) {
        try {
            var jaxbContext = JAXBContext.newInstance(Envelope.class);
            var unmarshaller = jaxbContext.createUnmarshaller();
            return (Envelope) unmarshaller.unmarshal(new StringReader(ecbXmlData));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse ECB rates", e);
        }
    }

    @Override
    public boolean isReady() {
        return cubes != null && !cubes.isEmpty();
    }

    @Override
    public double getExchangeRate(String currencyPair) {
        var currencies = currencyPair.split("/");
        var base = currencies[0];
        var target = currencies[1];
        double baseRate = cubes.get(base).getRate();
        double targetRate = cubes.get(target).getRate();
        currenciesNumberToCall.compute(base, (k, v) -> + 1);
        currenciesNumberToCall.compute(target, (k, v) -> + 1);
        return targetRate / baseRate;
    }

    @Override
    public Map<String, Integer> getCurrenciesNumberCall() {
        return currenciesNumberToCall;
    }
}
