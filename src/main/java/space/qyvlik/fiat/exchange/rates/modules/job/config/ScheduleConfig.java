package space.qyvlik.fiat.exchange.rates.modules.job.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 定时器的多线程支持
 */

@Profile("scheduling")
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // @EnableScheduling 和 @Scheduled默认是基于单线程
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(3));
    }
}

