package io.literpc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author kevin Pu
 */
public class RpcRefererBean<T> implements FactoryBean, ApplicationContextAware {

    private transient ApplicationContext applicationContext;

    private Class<?> interfaceClass;

    private transient volatile Object ref;

    private LiterpcProperties literpcProperties;

    @Override
    public Object getObject() {
        return new Object();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        literpcProperties = applicationContext.getBean(LiterpcProperties.class);
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
