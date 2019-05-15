package space.qyvlik.fiat.exchange.rates.modules.rate.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import space.qyvlik.fiat.exchange.rates.common.BaseService;
import space.qyvlik.fiat.exchange.rates.modules.provider.entity.result.FiatExchangeRate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RateCacheService extends BaseService {
    public static final String RATE_KEY_PREFIX = "r:";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @CachePut(cacheManager = "rateCacheManager", cacheNames = "rates", key = "#fiatRate.base + '/'+ #fiatRate.quote")
    public void putFiatRate(FiatExchangeRate fiatRate) {
        String rateKey = RATE_KEY_PREFIX
                + fiatRate.getProvider()
                + ":"
                + fiatRate.getBase() + "/" + fiatRate.getQuote();
        String jsonStr = JSON.toJSONString(fiatRate);
        redisTemplate.opsForValue().set(rateKey, jsonStr, 14400, TimeUnit.SECONDS);
    }

    @Cacheable(cacheManager = "rateCacheManager", cacheNames = "rates", key = "#base + '/'+ #quote")
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
