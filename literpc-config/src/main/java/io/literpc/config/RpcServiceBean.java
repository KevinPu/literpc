package io.literpc.config;

import io.literpc.core.exporter.Exporter;
import io.literpc.core.invoker.Invoker;
import io.literpc.core.proxy.JdkProxyFactory;
import io.literpc.core.proxy.ProxyFactory;
import io.literpc.core.url.URL;
import io.literpc.protocol.Protocol;
import io.literpc.protocol.defaultprotocol.DefaultProtocol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin Pu
 */
public class RpcServiceBean<T> implements InitializingBean, DisposableBean,
        ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, Serializable {

    private static final long serialVersionUID = 1508305705610944481L;

    private static final Protocol protocol = new DefaultProtocol();

    private static final ProxyFactory proxyFactory = new JdkProxyFactory();

    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    private transient ApplicationContext applicationContext;
    // interface type
    private String interfaceName;
    private Class<?> interfaceClass;
    // referer to interface impl
    private T ref;

    private LiterpcProperties literpcProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        this.literpcProperties = applicationContext.getBean(LiterpcProperties.class);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        export();
    }

    @SuppressWarnings("unchecked")
    private void export() {
        URL url = new URL(literpcProperties.getProtocol(), literpcProperties.getPort(), interfaceName);

        Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, url);

        Exporter<?> exporter = protocol.export(invoker);

        exporters.add(exporter);
    }

    @Override
    public void destroy() {
        upexport();
    }

    private void upexport() {
        if (!exporters.isEmpty()) {
            for (Exporter<?> exporter : exporters) {
                try {
                    exporter.unexport();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            exporters.clear();
        }
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }
}
