package com.xgk.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: xugongkai
 * @created: 2022-02-23 15:29
 */
@Component
public class ShareServiceBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("shareService".equals(beanName) && (bean instanceof ShareService)) {
            ShareService service = (ShareService) bean;
            service.setUid("Build UUID by beanPostProcessor, random flag:" + UUID.randomUUID().toString());
            return service;
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
