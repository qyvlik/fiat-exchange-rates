package space.qyvlik.fiat.exchange.rates.common.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class RateCacheConfig {

    @Value("${rate.cache.expire}")
    private Integer rateCacheExpire;

    @Bean("rateCacheManager")
    public CacheManager rateCacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager("rates");
        cacheManager.setCacheBuilder(
                CacheBuilder.newBuilder().
                        expireAfterWrite(rateCacheExpire, TimeUnit.SECONDS).
                        maximumSize(1000));
        return cacheManager;

    }

}
