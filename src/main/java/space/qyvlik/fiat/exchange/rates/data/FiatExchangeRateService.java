package space.qyvlik.fiat.exchange.rates.data;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import space.qyvlik.fiat.exchange.rates.base.AbstractFiatExchangeRatesProvider;
import space.qyvlik.fiat.exchange.rates.base.BaseService;
import space.qyvlik.fiat.exchange.rates.entity.Account;
import space.qyvlik.fiat.exchange.rates.entity.FiatExchangeRate;
import space.qyvlik.fiat.exchange.rates.provider.ProviderFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class FiatExchangeRateService extends BaseService {
    private static final String RATE_KEY_PREFIX = "r:";
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProviderFactory providerFactory;
    @Autowired
    private Environment environment;

    public void syncFiatExchangeRateList() {
        logger.info("syncFiatExchangeRateList start");

        List<AbstractFiatExchangeRatesProvider> providerList =
                providerFactory.providerList();

        if (providerList == null || providerList.size() == 0) {
            logger.warn("syncFiatExchangeRateList return, providerList is empty");
            return;
        }

        for (AbstractFiatExchangeRatesProvider provider : providerList) {
            syncFiatExchangeRateList(provider);
        }

        logger.info("syncFiatExchangeRateList end");
    }

    public void syncFiatExchangeRateList(AbstractFiatExchangeRatesProvider provider) {
        String enable = environment.getProperty("provider." + provider.getProvider() + ".enable");

        if (StringUtils.isBlank(enable) || !Boolean.parseBoolean(enable)) {
            logger.warn("syncFiatExchangeRateList provider:{} disable", provider.getProvider());
            return;
        }

        String key = environment.getProperty("provider." + provider.getProvider() + ".key");
        String plan = environment.getProperty("provider." + provider.getProvider() + ".plan");
        String username = environment.getProperty("provider." + provider.getProvider() + ".username");

        Account account = new Account();
        account.setPlan(plan);
        account.setAccessKey(key);
        account.setProvider(provider.getProvider());
        account.setUserName(username);

        List<FiatExchangeRate> rateList = null;

        try {
            rateList = provider.findExchangeRateList(account);
        } catch (Exception e) {
            logger.error("syncFiatExchangeRateList fail : provider:{}, error:{}",
                    provider.getProvider(), e.getMessage());
            return;
        }

        if (rateList == null || rateList.size() == 0) {
            logger.warn("syncFiatExchangeRateList fail : rateList is empty, provider:{}",
                    provider.getProvider());
            return;
        }

        for (FiatExchangeRate fiatRate : rateList) {
            String rateKey = RATE_KEY_PREFIX
                    + fiatRate.getProvider()
                    + ":"
                    + fiatRate.getBase() + "/" + fiatRate.getQuote();
            String jsonStr = JSON.toJSONString(fiatRate);
            redisTemplate.opsForValue().set(rateKey, jsonStr, 14400, TimeUnit.SECONDS);
        }
        logger.info("syncFiatExchangeRateList end provider:{}", provider.getProvider());
    }

    public List<FiatExchangeRate> findFiatExchangeRateListByBaseAndQuote(String base, String quote) {
        // r:*:base/quote
        if (StringUtils.isBlank(base)) {
            base = "*";
        }

        if (StringUtils.isBlank(quote)) {
            quote = "*";
        }

        Set<String> rateKeySet = redisTemplate.keys(RATE_KEY_PREFIX + "*:" + base.toUpperCase() + "/" + quote.toUpperCase());

        List<FiatExchangeRate> rateList = Lists.newArrayList();

        if (rateKeySet == null || rateKeySet.isEmpty()) {
            return rateList;
        }

        for (String rateKey : rateKeySet) {
            String jsonStr = redisTemplate.opsForValue().get(rateKey);
            if (StringUtils.isNotBlank(jsonStr) && jsonStr.startsWith("{")) {
                rateList.add(JSON.parseObject(jsonStr).toJavaObject(FiatExchangeRate.class));
            }
        }

        return rateList;
    }
}
