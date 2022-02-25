package com.xgk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: xugongkai
 * @created: 2022-02-17 17:41
 */
@SpringBootTest(classes = {
        App.class,
        BeanDefinitionRegistryPostProcessorCases.class
})
@RunWith(SpringRunner.class)
public class PersonSpringBootTest {

    //required=false: 注释某些方法后，部分bean不会注入。
    @Autowired(required = false)
    private BeanDefinitionRegistryPostProcessorCases.Person person;
    @Autowired(required = false)
    private BeanDefinitionRegistryPostProcessorCases.Student student;
    @Autowired(required = false)
    private BeanDefinitionRegistryPostProcessorCases.PineApple pineApple;
    @Autowired(required = false)
    private ApplicationContext ctx;

    @Test
    public void testBeanFactoryRegister() {
        // 放开 com.xgk.BeanDefinitionRegistryPostProcessorCases.postProcessBeanFactory() 方法中的 registerByBeanFactoryPostProcessor()方法
        // 测试完毕后注释掉该方法
        System.out.println(pineApple);
    }

    @Test
    public void testRootBeanDefinition() {
        // 放开 com.xgk.BeanDefinitionRegistryPostProcessorCases.postProcessBeanDefinitionRegistry()方法中的 registerByRootBeanDefinition()方法
        // 测试完毕后注释掉该方法
        System.out.println(person);
    }

    @Test
    public void testChildBeanDefinition() {
        // 放开 com.xgk.BeanDefinitionRegistryPostProcessorCases.postProcessBeanDefinitionRegistry()方法中的 registerByChildBeanDefinition()方法
        // 测试完毕后注释掉该方法
        System.out.println(person);
        System.out.println(student);
    }

    @Test
    public void testGenericBeanDefinition() {
        // 放开 com.xgk.BeanDefinitionRegistryPostProcessorCases.postProcessBeanDefinitionRegistry()方法中的 registerByGenericBeanDefinition()方法
        // 测试完毕后注释掉该方法
        System.out.println(person);
        System.out.println(student);
    }
    
    @Test
    public void testAnnotationGenericBeanDefinition() {
        // 放开 com.xgk.BeanDefinitionRegistryPostProcessorCases.postProcessBeanDefinitionRegistry()方法中的 registerByAnnotatedGenericBeanDefinition()方法
        // 测试完毕后注释掉该方法
        System.out.println(person);
        ConfigurableListableBeanFactory beanFactory = ((AnnotationConfigApplicationContext) ctx).getBeanFactory();
        BeanDefinition personDf = beanFactory.getBeanDefinition("person");

        AnnotationMetadata metadata = ((AnnotatedGenericBeanDefinition) personDf).getMetadata();
        Map<String, Object> rawAnnotationAttributes = metadata.getAnnotationAttributes(BeanDefinitionRegistryPostProcessorCases.TestAnnotation.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(rawAnnotationAttributes);
        String[] values = annotationAttributes.getStringArray("values");
        System.out.println(Arrays.toString(values));

        MethodMetadata rawFactoryMethodMetadata = ((AnnotatedGenericBeanDefinition) personDf).getFactoryMethodMetadata();
        AnnotationAttributes factoryAnnotationAttributes = AnnotationAttributes.fromMap(rawFactoryMethodMetadata.getAnnotationAttributes(BeanDefinitionRegistryPostProcessorCases.TestAnnotation.class.getName()));
        String values1 = factoryAnnotationAttributes.getString("values");
        System.out.println(values1);
    }

    @Test
    public void testOtherBeanDefinitionType() {
        ConfigurableListableBeanFactory beanFactory = ((AnnotationConfigApplicationContext) ctx).getBeanFactory();
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("dog");
        System.out.println(beanDefinition.getClass().getName()); //@Bean方式注入：ConfigurationClassBeanDefinition

        BeanDefinition app = beanFactory.getBeanDefinition("app");
        System.out.println(app.getClass().getName()); //@Configuration方式注入：AnnotatedGenericBeanDefinition

        BeanDefinition fruitService = beanFactory.getBeanDefinition("fruitService");
        System.out.println(fruitService.getClass().getName()); //@Controller @Service方式注入：ScannedGenericBeanDefinition
        System.out.println("Hi:)");
    }

}
