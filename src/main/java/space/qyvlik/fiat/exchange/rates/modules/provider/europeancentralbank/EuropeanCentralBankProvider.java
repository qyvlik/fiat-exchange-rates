package space.qyvlik.fiat.exchange.rates.modules.provider.europeancentralbank;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import space.qyvlik.fiat.exchange.rates.common.AbstractFiatExchangeRatesProvider;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.request.Account;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.result.FiatExchangeRate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EuropeanCentralBankProvider extends AbstractFiatExchangeRatesProvider {

    @Autowired
    private RestTemplate restTemplate;
    private String host = "www.ecb.europa.eu";


    public EuropeanCentralBankProvider() {
        super("EuropeanCentralBank");
    }

    @Override
    public List<FiatExchangeRate> findExchangeRateList(Account account) {

        final String path = "/stats/eurofxref/eurofxref-daily.xml";

        String url = "https://" + host + path;

        List<FiatExchangeRate> list = Lists.newArrayList();

        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);

            String body = response.getBody();

            String[] lines = body.split("\n");

            for (String line : lines) {

                // <Cube currency='USD' rate='1.1506'/>
                String[] data = line
                        .trim()
                        .replace("<Cube currency='", "")
                        .replace("\'/>", "")
                        .split("\' rate=\'");

                if (data.length <= 1) {
                    continue;
                }

                String baseCurrency = "EUR";
                String quoteCurrency = data[0];
                BigDecimal rate = new BigDecimal(data[1]);

                FiatExchangeRate fiatRate = new FiatExchangeRate();
                fiatRate.setProvider(getProvider());
                fiatRate.setBase(baseCurrency);
                fiatRate.setQuote(quoteCurrency);
                fiatRate.setRate(rate);
                fiatRate.setTs(System.currentTimeMillis());

                list.add(fiatRate);
            }

        } catch (Exception e) {
            logger.error("findExchangeRateList fail : error:{}", e.getMessage());
        }

        return list;
    }
}
