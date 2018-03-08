package io.literpc.config;

import io.literpc.core.invoker.Invoker;
import io.literpc.core.proxy.JdkProxyFactory;
import io.literpc.core.proxy.ProxyFactory;
import io.literpc.core.url.URL;
import io.literpc.protocol.Protocol;
import io.literpc.protocol.defaultprotocol.DefaultProtocol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author kevin Pu
 */
public class RpcRefererBean<T> implements FactoryBean, ApplicationContextAware {

    private static final Protocol protocol = new DefaultProtocol();

    private static final ProxyFactory proxyFactory = new JdkProxyFactory();

    private Class<?> interfaceClass;

    private transient volatile T ref;

    private LiterpcProperties literpcProperties;

    @Override
    public Object getObject() {

        if (ref != null) {
            return ref;
        }
        ref = createProxy();

        return ref;
    }

    @SuppressWarnings("unchecked")
    private T createProxy() {
        URL url = new URL(literpcProperties.getProtocol(), literpcProperties.getPort(), literpcProperties.getAppname());
        Invoker<?> invoker = protocol.refer(interfaceClass, url);
        return (T) proxyFactory.getProxy(invoker);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        literpcProperties = applicationContext.getBean(LiterpcProperties.class);
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
