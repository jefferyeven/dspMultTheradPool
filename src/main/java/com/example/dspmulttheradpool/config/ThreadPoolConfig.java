package com.example.dspmulttheradpool.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "task.pool")
@Getter
@Setter
public class ThreadPoolConfig {

    private int initCorePoolSize;

    private int initMaxPoolSize;

    private int initKeepAliveSeconds;

    private int initQueueCapacity;

    private int minSetCorePoolSize;

    private int minSetMaxPoolSize;

    private int maxSetCorePoolSize;

    private int maxSetMaxPoolSize;

    private int addPoolSize;
}
