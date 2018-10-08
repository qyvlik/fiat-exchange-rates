package space.qyvlik.fiat.exchange.rates.provider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.qyvlik.fiat.exchange.rates.base.AbstractFiatExchangeRatesProvider;
import space.qyvlik.fiat.exchange.rates.base.BaseService;
import space.qyvlik.fiat.exchange.rates.provider.currencylayer.CurrencyLayerProvider;
import space.qyvlik.fiat.exchange.rates.provider.europeancentralbank.EuropeanCentralBankProvider;
import space.qyvlik.fiat.exchange.rates.provider.oneforge.OneForgeProvider;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class ProviderFactory extends BaseService {
    private Map<String, AbstractFiatExchangeRatesProvider> providerMap = Maps.newConcurrentMap();

    @Autowired
    private CurrencyLayerProvider currencyLayerProvider;

    @Autowired
    private OneForgeProvider oneForgeProvider;

    @Autowired
    private EuropeanCentralBankProvider europeanCentralBankProvider;

    @PostConstruct
    protected void init() {
        providerMap.put(currencyLayerProvider.getProvider(), currencyLayerProvider);
        providerMap.put(oneForgeProvider.getProvider(), oneForgeProvider);
        providerMap.put(europeanCentralBankProvider.getProvider(), europeanCentralBankProvider);
    }

    public List<AbstractFiatExchangeRatesProvider> providerList() {
        return Lists.newArrayList(providerMap.values());
    }
}
