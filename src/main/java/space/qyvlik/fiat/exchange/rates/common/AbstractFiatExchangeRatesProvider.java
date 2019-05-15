package space.qyvlik.fiat.exchange.rates.common;

import space.qyvlik.fiat.exchange.rates.modules.provider.entity.request.Account;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.result.FiatExchangeRate;

import java.util.List;

public abstract class AbstractFiatExchangeRatesProvider extends BaseService {
    protected String provider;


    public AbstractFiatExchangeRatesProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public abstract List<FiatExchangeRate> findExchangeRateList(Account account);
}
