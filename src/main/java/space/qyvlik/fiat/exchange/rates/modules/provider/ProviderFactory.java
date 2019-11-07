package space.qyvlik.fiat.exchange.rates.modules.provider;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.qyvlik.fiat.exchange.rates.common.base.BaseService;

import java.util.List;

@Service
public class ProviderFactory extends BaseService {

    @Autowired
    private List<AbstractFiatExchangeRatesProvider> providers;

    public List<AbstractFiatExchangeRatesProvider> providerList() {
        return ImmutableList.<AbstractFiatExchangeRatesProvider>builder().addAll(providers).build();
    }
}
