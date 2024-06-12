package com.rayanbourse.ers;

import com.rayanbourse.ers.service.ECBService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestTemplate {
    @Value("${ecb_url}")
    private String ecbUrl;
    private String MOCK_XML_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<gesmes:Envelope xmlns:gesmes=\"http://www.gesmes.org/xml/2002-08-01\" xmlns=\"http://www.ecb.int/vocabulary/2002-08-01/eurofxref\">\n" +
            "\t<gesmes:subject>Reference rates</gesmes:subject>\n" +
            "\t<gesmes:Sender>\n" +
            "\t\t<gesmes:name>European Central Bank</gesmes:name>\n" +
            "\t</gesmes:Sender>\n" +
            "\t<Cube>\n" +
            "\t\t<Cube time='2024-06-11'>\n" +
            "\t\t\t<Cube currency='USD' rate='1.0765'/>\n" +
            "\t\t\t<Cube currency='JPY' rate='169.35'/>\n" +
            "\t\t\t<Cube currency='BGN' rate='1.9558'/>\n" +
            "\t\t\t<Cube currency='CZK' rate='24.686'/>\n" +
            "\t\t\t<Cube currency='DKK' rate='7.4588'/>\n" +
            "\t\t\t<Cube currency='GBP' rate='0.84365'/>\n" +
            "\t\t\t<Cube currency='HUF' rate='395.28'/>\n" +
            "\t\t\t<Cube currency='PLN' rate='4.3385'/>\n" +
            "\t\t\t<Cube currency='RON' rate='4.9768'/>\n" +
            "\t\t\t<Cube currency='SEK' rate='11.2345'/>\n" +
            "\t\t\t<Cube currency='CHF' rate='0.9641'/>\n" +
            "\t\t\t<Cube currency='ISK' rate='149.50'/>\n" +
            "\t\t\t<Cube currency='NOK' rate='11.4675'/>\n" +
            "\t\t\t<Cube currency='TRY' rate='34.8563'/>\n" +
            "\t\t\t<Cube currency='AUD' rate='1.6280'/>\n" +
            "\t\t\t<Cube currency='BRL' rate='5.7912'/>\n" +
            "\t\t\t<Cube currency='CAD' rate='1.4795'/>\n" +
            "\t\t\t<Cube currency='CNY' rate='7.8086'/>\n" +
            "\t\t\t<Cube currency='HKD' rate='8.4082'/>\n" +
            "\t\t\t<Cube currency='IDR' rate='17552.60'/>\n" +
            "\t\t\t<Cube currency='ILS' rate='3.9902'/>\n" +
            "\t\t\t<Cube currency='INR' rate='89.9490'/>\n" +
            "\t\t\t<Cube currency='KRW' rate='1481.35'/>\n" +
            "\t\t\t<Cube currency='MXN' rate='20.2743'/>\n" +
            "\t\t\t<Cube currency='MYR' rate='5.0784'/>\n" +
            "\t\t\t<Cube currency='NZD' rate='1.7528'/>\n" +
            "\t\t\t<Cube currency='PHP' rate='63.094'/>\n" +
            "\t\t\t<Cube currency='SGD' rate='1.4553'/>\n" +
            "\t\t\t<Cube currency='THB' rate='39.475'/>\n" +
            "\t\t\t<Cube currency='ZAR' rate='20.0486'/>\n" +
            "\t\t</Cube>\n" +
            "\t</Cube>\n" +
            "</gesmes:Envelope>";

    @MockBean
    private RestTemplate restClient;

    @Autowired
    public ECBService ecbService;

    @BeforeEach
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(restClient.getForObject(ecbUrl, String.class)).thenReturn(MOCK_XML_RESPONSE);
        getFillDataMethod().invoke(ecbService);
        await().until(() -> ecbService.isReady());
    }

    private Method getFillDataMethod() throws NoSuchMethodException {
        Method method = ECBService.class.getDeclaredMethod("fillCubeData");
        method.setAccessible(true);
        return method;
    }
}
