package space.qyvlik.fait.exchange.rates.base;

import org.springframework.stereotype.Service;
import space.qyvlik.fait.exchange.rates.entity.Account;
import space.qyvlik.fait.exchange.rates.entity.FiatExchangeRate;

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
