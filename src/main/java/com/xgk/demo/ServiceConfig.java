package com.xgk.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xugongkai
 * @created: 2022-02-23 15:24
 */
@Configuration(proxyBeanMethods = true)
public class ServiceConfig {

    @Bean
    ServiceA serviceA() {
        ShareService shareService = shareService();
        return new ServiceA(shareService);
    }

    @Bean
    ServiceB serviceB() {
        ShareService shareService = shareService();
        return new ServiceB(shareService);
    }

    @Bean
    // 当proxyBeanMethods = true且还想serviceA, serviceB注入的shareService不同时，可以考虑使用@Scope调整
    //@Scope("prototype")
    ShareService shareService() {
        return new ShareService();
    }

}
