package com.xgk;

import lombok.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;
import java.math.BigDecimal;

/**
 * @author: xugongkai
 * @created: 2022-02-17 18:06
 */
public class BeanDefinitionRegistryPostProcessorCases implements BeanDefinitionRegistryPostProcessor {

    @Bean
    Dog dog() {
        return new Dog("来福");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //registerByRootBeanDefinition(registry);
        //registerByChildBeanDefinition(registry);
        //registerByGenericBeanDefinition(registry);
        registerByAnnotatedGenericBeanDefinition(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //registerByBeanFactoryPostProcessor(beanFactory);
    }


    private void registerByBeanFactoryPostProcessor(ConfigurableListableBeanFactory factory) {
        PineApple pineApple = new PineApple("菠萝", BigDecimal.valueOf(12.5D));
        factory.registerSingleton("pineApple", pineApple);
    }

    private void registerByRootBeanDefinition(BeanDefinitionRegistry registry) {
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("name", "tom");
        mutablePropertyValues.add("age", 15);
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class, constructorArgumentValues, mutablePropertyValues);
        registry.registerBeanDefinition("person", rootBeanDefinition);
    }

    private void registerByChildBeanDefinition(BeanDefinitionRegistry registry) {
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("name", "tom");
        mutablePropertyValues.add("age", 15);
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class, constructorArgumentValues, mutablePropertyValues);
        registry.registerBeanDefinition("person", rootBeanDefinition);

        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("person");
        ConstructorArgumentValues studentConstructValues = new ConstructorArgumentValues();
        studentConstructValues.addIndexedArgumentValue(0, "middleSchool");
        childBeanDefinition.setConstructorArgumentValues(studentConstructValues);
        childBeanDefinition.setBeanClass(Student.class);
        registry.registerBeanDefinition("student", childBeanDefinition);
    }

    private void registerByGenericBeanDefinition(BeanDefinitionRegistry registry) {
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("name", "tom");
        mutablePropertyValues.add("age", 15);
        GenericBeanDefinition personBeanDefinition = new GenericBeanDefinition();
        personBeanDefinition.setBeanClass(Person.class);
        personBeanDefinition.setPropertyValues(mutablePropertyValues);
        registry.registerBeanDefinition("person", personBeanDefinition);

        ConstructorArgumentValues studentConstructValues = new ConstructorArgumentValues();
        studentConstructValues.addIndexedArgumentValue(0, "middleSchool");
        GenericBeanDefinition studentBeanDefinition = new GenericBeanDefinition();
        studentBeanDefinition.setConstructorArgumentValues(studentConstructValues);
        studentBeanDefinition.setBeanClass(Student.class);
        studentBeanDefinition.setParentName("person");
        registry.registerBeanDefinition("student", studentBeanDefinition);
    }

    private void registerByAnnotatedGenericBeanDefinition(BeanDefinitionRegistry registry) {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(Person.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("name", "alice");
        propertyValues.add("age", "23");
        beanDefinition.setPropertyValues(propertyValues);
        beanDefinition.setFactoryBeanName("personFactoryBean");
        beanDefinition.setFactoryMethodName("init");
        registry.registerBeanDefinition("person", beanDefinition);
    }

    @Data
    @TestAnnotation(values = {"aa", "bb", "cc"})
    public static class Person {
        private String name;
        private Integer age;
    }

    @Getter
    @Setter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Student extends Person{
        private final String schoolName;
        public Student(String schoolName) {
            super();
            this.schoolName = schoolName;
        }
    }

    @Data
    public static class PineApple{
        private final String cnName;
        private final BigDecimal netPrice;
        public PineApple(String cnName, BigDecimal netPrice) {
            this.cnName = cnName;
            this.netPrice = netPrice;
        }
    }

    @Data
    public static class Dog{
        private final String name;
        public Dog(String name) {
            this.name = name;
        }
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Inherited
    public @interface TestAnnotation {
        String[] values();
    }

}
