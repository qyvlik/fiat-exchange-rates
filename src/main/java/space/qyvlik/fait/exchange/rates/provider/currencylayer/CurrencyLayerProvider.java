package space.qyvlik.fait.exchange.rates.provider.currencylayer;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import space.qyvlik.fait.exchange.rates.base.AbstractFiatExchangeRatesProvider;
import space.qyvlik.fait.exchange.rates.entity.Account;
import space.qyvlik.fait.exchange.rates.entity.FiatExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyLayerProvider extends AbstractFiatExchangeRatesProvider {

    @Autowired
    private RestTemplate restTemplate;

    private String host = "apilayer.net";

    public CurrencyLayerProvider() {
        super("CurrencyLayer");
    }

    @Override
    public List<FiatExchangeRate> findExchangeRateList(Account account) {
        if (StringUtils.isBlank(account.getAccessKey())) {
            throw new RuntimeException("provider:" + provider + ", accesskey is empty");
        }

        if (StringUtils.isBlank(account.getPlan())) {
            throw new RuntimeException("provider:" + provider + ", plan is empty");
        }

        final String path = "/api/live?access_key=" + account.getAccessKey() + "&prettyprint=1";

        List<FiatExchangeRate> list = Lists.newArrayList();

        try {
            String url = host + path;

            if (account.getPlan().equalsIgnoreCase("free")) {
                url = "http://" + url;
            } else {
                url = "https://" + url;
            }

            ResponseEntity<CurrencyLayerQuotes> response =
                    restTemplate.getForEntity(url, CurrencyLayerQuotes.class);

            CurrencyLayerQuotes body = response.getBody();

            if (body == null || body.getSuccess() == null || !body.getSuccess()) {
                if (body == null) {
                    logger.error("findExchangeRateList fail : error: response is null");
                } else {
                    logger.error("findExchangeRateList fail : error:{}", body.getError());
                }
                return list;
            }

            Long ts = body.getTimestamp();
            String baseCurrency = body.getSource();
            Map<String, BigDecimal> quotes = body.getQuotes();

            for (String pair : quotes.keySet()) {
                String quoteCurrency = pair.replace(baseCurrency, "");
                if (StringUtils.isBlank(quoteCurrency)) {
                    continue;
                }

                FiatExchangeRate fiatRate = new FiatExchangeRate();

                fiatRate.setBase(baseCurrency.toUpperCase());
                fiatRate.setQuote(quoteCurrency.toUpperCase());
                fiatRate.setRate(quotes.get(pair));
                fiatRate.setTs(ts * 1000);
                fiatRate.setProvider(getProvider());

                list.add(fiatRate);
            }

        } catch (Exception e) {
            logger.error("findExchangeRateList fail : error:{}", e.getMessage());
        }
        return list;
    }
}
