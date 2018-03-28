package io.literpc.config;

import io.literpc.config.annotation.RpcReferer;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;

/**
 * @author kevin Pu
 */
public class RpcRefererAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
        implements MergedBeanDefinitionPostProcessor, ApplicationContextAware {

    public static final String BEAN_NAME = "rpcRefererAnnotationBeanPostProcessor";

    private ApplicationContext applicationContext;

    private final ConcurrentMap<String, InjectionMetadata> injectionMetadataCache =
            new ConcurrentHashMap<>(256);

    private final ConcurrentMap<String, RpcRefererBean<?>> rpcRefererBeansCache =
            new ConcurrentHashMap<>();

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = findRpcRefererMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of @RpcReferer dependencies failed", ex);
        }
        return pvs;
    }

    private InjectionMetadata findRpcRefererMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        // Fall back to class name as cache key, for backwards compatibility with custom callers.
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        // Quick check on the concurrent map first, with minimal locking.
        InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    try {
                        metadata = buildRpcRefererMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, metadata);
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
                                "] for RpcReferer metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return metadata;
    }

    private InjectionMetadata buildRpcRefererMetadata(Class<?> clazz) {
        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();

        elements.addAll(findFieldRpcRefererMetadata(clazz));

        return new InjectionMetadata(clazz, elements);
    }

    private List<InjectionMetadata.InjectedElement> findFieldRpcRefererMetadata(Class<?> clazz) {
        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();

        ReflectionUtils.doWithFields(clazz, (Field field) -> {
            RpcReferer rpcReferer = getAnnotation(field, RpcReferer.class);
            if (rpcReferer != null) {
                elements.add(new RpcRefererFieldElement(field, rpcReferer));
            }
        });

        return elements;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private class RpcRefererFieldElement extends InjectionMetadata.InjectedElement {

        private final Field field;

        private final RpcReferer rpcReferer;

        private RpcRefererFieldElement(Field field, RpcReferer rpcReferer) {
            super(field, null);
            this.field = field;
            this.rpcReferer = rpcReferer;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {

            Class<?> rpcRefererClass = field.getType();

            Object rpcRefererBean = buildpcRefererBean(rpcReferer, rpcRefererClass);

            ReflectionUtils.makeAccessible(field);

            field.set(bean, rpcRefererBean);
        }
    }

    private Object buildpcRefererBean(RpcReferer rpcReferer, Class<?> rpcRefererClass) {
        String rpcRefererBeanCacheKey = rpcRefererClass.getName();

        RpcRefererBean<?> rpcRefererBean = rpcRefererBeansCache.get(rpcRefererBeanCacheKey);

        if (rpcRefererBean == null) {
            rpcRefererBean = new RpcRefererBean<>();

            rpcRefererBean.setApplicationContext(applicationContext);
            rpcRefererBean.setInterfaceClass(rpcRefererClass);
            rpcRefererBeansCache.putIfAbsent(rpcRefererBeanCacheKey, rpcRefererBean);

        }
        return rpcRefererBean.getObject();
    }
}
