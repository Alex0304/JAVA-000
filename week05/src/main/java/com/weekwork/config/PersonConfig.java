package com.weekwork.config;

import com.weekwork.dynamic.service.PersonService;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class PersonConfig implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        final RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(PersonService.class);
        rootBeanDefinition.setInitMethodName("init");
        beanDefinitionRegistry.registerBeanDefinition("personService",rootBeanDefinition);
    }
}
