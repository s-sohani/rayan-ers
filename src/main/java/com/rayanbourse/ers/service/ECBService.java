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
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
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

    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    private void fillCubeData() {
        var ecbXmlData = restClient.getForObject(ecbUrl, String.class);
        var envelop = convertECBXmlToObject(ecbXmlData);
        cubes = envelop.getCube().getCubes().stream().findAny().orElseThrow().getCubeData().stream()
                .collect(Collectors.toMap(CubeData::getCurrency, Function.identity()));
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
    public double getExchangeRate(String currencyPair) {
        var currencies = currencyPair.split("/");
        var base = currencies[0];
        var target = currencies[1];
        double baseRate = base.equals("EUR") ? 1.0 : cubes.get(base).getRate();
        double targetRate = target.equals("EUR") ? 1.0 : cubes.get(target).getRate();
        return targetRate / baseRate;
    }
}
