package space.qyvlik.fiat.exchange.rates.modules.provider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.qyvlik.fiat.exchange.rates.common.base.BaseService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class ProviderFactory extends BaseService {
    private Map<String, AbstractFiatExchangeRatesProvider> providerMap = Maps.newConcurrentMap();

    @Autowired
    private List<AbstractFiatExchangeRatesProvider> providers;

    @PostConstruct
    protected void init() {
        for (AbstractFiatExchangeRatesProvider provider : providers) {
            providerMap.put(provider.getProvider(), provider);
        }
    }

    public List<AbstractFiatExchangeRatesProvider> providerList() {
        return Lists.newArrayList(providerMap.values());
    }
}
