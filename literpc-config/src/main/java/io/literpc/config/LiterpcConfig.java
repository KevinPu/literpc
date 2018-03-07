package io.literpc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kevin Pu
 */
@Configuration
public class LiterpcConfig {

    @Value("${spring.literpc.appname}")
    private String appname;
    @Value("${spring.literpc.registry}")
    private String registry;
    @Value("${spring.literpc.protocol}")
    private String protocol;
    @Value("${spring.literpc.port:20800}")
    private int port;

    @Bean
    public LiterpcProperties literpcProperties() {
        LiterpcProperties literpcProperties = new LiterpcProperties();
        literpcProperties.setAppname(appname);
        literpcProperties.setRegistry(registry);
        literpcProperties.setProtocol(protocol);
        literpcProperties.setPort(port);
        return literpcProperties;
    }
}
