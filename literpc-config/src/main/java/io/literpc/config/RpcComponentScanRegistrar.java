package io.literpc.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * @author kevin Pu
 */
public class RpcComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);

        registerRpcServiceAnnotationBeanPostProcessor(packagesToScan, registry);

        registerRpcRefererAnnotationBeanPostProcessor(registry);
    }

    private void registerRpcServiceAnnotationBeanPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = rootBeanDefinition(RpcServiceAnnotationBeanPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
    }

    private void registerRpcRefererAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {
        String beanName = RpcRefererAnnotationBeanPostProcessor.BEAN_NAME;
        if (!registry.containsBeanDefinition(beanName)) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RpcRefererAnnotationBeanPostProcessor.class);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private Set<String> getPackagesToScan(AnnotationMetadata importingClassMetadata) {
        return Collections.singleton(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
    }
}
