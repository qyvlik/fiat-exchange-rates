package space.qyvlik.fiat.exchange.rates.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfig {

    @Bean
    public RestTemplate restTemplate() {
        return (new RestTemplateBuilder())
                .setConnectTimeout(30 * 1000)
                .setReadTimeout(30 * 1000)
                .build();
    }

}
