package space.qyvlik.fiat.exchange.rates.modules.provider.openexchangerates;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import space.qyvlik.fiat.exchange.rates.common.AbstractFiatExchangeRatesProvider;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.request.Account;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.result.FiatExchangeRate;

import java.util.List;

@Service
public class OpenExchangeRatesProvider extends AbstractFiatExchangeRatesProvider {

    @Autowired
    private RestTemplate restTemplate;

    private String host = "openexchangerates.org";

    public OpenExchangeRatesProvider() {
        super("OpenExchangeRates");
    }

    @Override
    public List<FiatExchangeRate> findExchangeRateList(Account account) {

        if (StringUtils.isBlank(account.getAccessKey())) {
            throw new RuntimeException("provider:" + provider + ", accesskey is empty");
        }

        if (StringUtils.isBlank(account.getPlan())) {
            throw new RuntimeException("provider:" + provider + ", plan is empty");
        }

        List<FiatExchangeRate> list = Lists.newArrayList();

        final String path = "/api/latest.json?app_id=" + account.getAccessKey();

        String url = "https://" + host + path;

        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);

            String body = response.getBody();
            if (StringUtils.isBlank(body)) {
                logger.error("findExchangeRateList fail : error: body is empty");
                return list;
            }
            JSONObject obj = JSON.parseObject(body);
            if (obj.containsKey("error") && obj.getBoolean("error")) {
                logger.error("findExchangeRateList fail : obj:{}", obj);
                return null;
            }

            OpenExchangeRatesLatest latest = obj.toJavaObject(OpenExchangeRatesLatest.class);

            if (StringUtils.isBlank(latest.getBase())) {
                logger.error("findExchangeRateList fail : latest lost base obj:{}", obj);
                return null;
            }

            String base = latest.getBase();
            Long ts = latest.getTimestamp() * 1000;
            for (String quote : latest.getRates().keySet()) {
                FiatExchangeRate fiatRate = new FiatExchangeRate();

                fiatRate.setProvider(getProvider());
                fiatRate.setQuote(quote);
                fiatRate.setBase(base);
                fiatRate.setTs(ts);
                fiatRate.setRate(latest.getRates().get(quote));

                list.add(fiatRate);
            }

        } catch (Exception e) {
            logger.error("findExchangeRateList fail : error:{}", e.getMessage());
        }


        return list;
    }
}
