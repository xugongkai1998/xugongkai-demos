package com.xgk;

import org.springframework.stereotype.Component;

/**
 * @author: xugongkai
 * @created: 2022-02-25 16:22
 */
@Component
@BeanDefinitionRegistryPostProcessorCases.TestAnnotation(values = {"tom", "jim", "alice", "bob"})
public class PersonFactoryBean {

    public BeanDefinitionRegistryPostProcessorCases.Person init() {
        System.out.println("执行init");
        return new BeanDefinitionRegistryPostProcessorCases.Person();
    }

}
