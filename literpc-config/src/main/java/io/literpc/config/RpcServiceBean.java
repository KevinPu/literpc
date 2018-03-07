package io.literpc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;

/**
 * @author kevin Pu
 */
public class RpcServiceBean<T> implements InitializingBean, ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent>, Serializable {

    private static final long serialVersionUID = 1508305705610944481L;

    private transient ApplicationContext applicationContext;
    // interface type
    private String interfaceName;
    private Class<?> interfaceClass;
    // reference to interface impl
    private T ref;

    private LiterpcProperties literpcProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.literpcProperties = applicationContext.getBean(LiterpcProperties.class);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        export();
    }


    private void export() {
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
