package space.qyvlik.fiat.exchange.rates.modules.rate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import space.qyvlik.fiat.exchange.rates.common.base.BaseService;
import space.qyvlik.fiat.exchange.rates.modules.provider.AbstractFiatExchangeRatesProvider;
import space.qyvlik.fiat.exchange.rates.modules.provider.ProviderFactory;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.request.Account;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.result.FiatExchangeRate;

import java.util.List;

@Service
public class FiatExchangeRateService extends BaseService {
    @Autowired
    private Environment environment;
    @Autowired
    private ProviderFactory providerFactory;
    @Autowired
    private RateCacheService rateCacheService;

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

    private void syncFiatExchangeRateList(AbstractFiatExchangeRatesProvider provider) {
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
            rateCacheService.putFiatRate(fiatRate);
        }
        logger.info("syncFiatExchangeRateList end provider:{}", provider.getProvider());
    }

    public List<FiatExchangeRate> findFiatExchangeRateListByBaseAndQuote(String base, String quote) {
        return rateCacheService.findFiatExchangeRateListByBaseAndQuote(base, quote);
    }
}
