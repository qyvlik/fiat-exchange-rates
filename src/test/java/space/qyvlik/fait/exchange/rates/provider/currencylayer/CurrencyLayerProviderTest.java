package space.qyvlik.fait.exchange.rates.provider.currencylayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import space.qyvlik.fait.exchange.rates.base.BaseService;
import space.qyvlik.fait.exchange.rates.entity.Account;
import space.qyvlik.fait.exchange.rates.entity.FiatExchangeRate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest("application-test.yml")
public class CurrencyLayerProviderTest extends BaseService {

    @Autowired
    private CurrencyLayerProvider currencyLayerProvider;

    @Value("${provider.CurrencyLayer.plan}")
    private String currencyLayerPlan;

    @Value("${provider.CurrencyLayer.key}")
    private String currencyLayerKey;

    @Test
    public void findExchangeRateList() throws Exception {
        Account account = new Account();

        account.setAccessKey(currencyLayerKey);
        account.setPlan(currencyLayerPlan);

        List<FiatExchangeRate> rateList = currencyLayerProvider.findExchangeRateList(account);

        logger.info("findExchangeRateList:{}", rateList);
    }

}