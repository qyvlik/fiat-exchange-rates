package space.qyvlik.fiat.exchange.rates.modules.provider.currencylayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import space.qyvlik.fiat.exchange.rates.common.BaseService;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.request.Account;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.result.FiatExchangeRate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest("application-test.yml")
public class CurrencyLayerProviderTest extends BaseService {

    @Autowired
    private CurrencyLayerProvider provider;

    @Value("${provider.CurrencyLayer.plan}")
    private String plan;

    @Value("${provider.CurrencyLayer.key}")
    private String key;

    @Test
    public void findExchangeRateList() throws Exception {
        Account account = new Account();

        account.setAccessKey(key);
        account.setPlan(plan);

        List<FiatExchangeRate> rateList = provider.findExchangeRateList(account);

        logger.info("findExchangeRateList:{}", rateList);
    }

}