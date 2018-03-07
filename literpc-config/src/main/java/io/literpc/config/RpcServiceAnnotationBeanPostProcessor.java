package io.literpc.config;

import io.literpc.config.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;
import static org.springframework.util.ClassUtils.resolveClassName;

/**
 * @author kevin Pu
 */
public class RpcServiceAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor,
        EnvironmentAware, ResourceLoaderAware, BeanClassLoaderAware {

    private final Set<String> packagesToScan;

    private Environment environment;

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    public RpcServiceAnnotationBeanPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false, environment, resourceLoader);

        BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

        scanner.addIncludeFilter(new AnnotationTypeFilter(RpcService.class));
        scanner.setBeanNameGenerator(beanNameGenerator);

        for (String packageToScan : packagesToScan) {

            // Registers @Service Bean first
            scanner.scan(packageToScan);

            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageToScan);

            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class<?> beanClass = resolveClassName(beanDefinition.getBeanClassName(), classLoader);
                Class<?>[] allInterfaces = beanClass.getInterfaces();
                Class<?> interfaceClass = null;
                if (allInterfaces.length > 0) {
                    interfaceClass = allInterfaces[0];
                }
                if (interfaceClass == null) {
                    throw new NoSuchBeanDefinitionException(beanDefinition.getBeanClassName());
                }
                String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
                AbstractBeanDefinition serviceBeanDefinition = rootBeanDefinition(RpcServiceBean.class)
                        .addPropertyReference("ref", beanName)
                        .addPropertyValue("interfaceClass", interfaceClass)
                        .addPropertyValue("interfaceName", interfaceClass.getName())
                        .getBeanDefinition();

                // ServiceBean Bean name
                String serviceBeanName = generateServiceBeanName(interfaceClass, beanName);

                registry.registerBeanDefinition(serviceBeanName, serviceBeanDefinition);
            }
        }
    }

    private String generateServiceBeanName(Class<?> interfaceClass, String annotatedServiceBeanName) {

        return "ServiceBean@" + interfaceClass.getName() + "#" + annotatedServiceBeanName;

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
