package io.literpc.config;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author kevin Pu
 */
public class RpcRefererBean implements FactoryBean {

    private transient volatile Object ref;

    @Override
    public Object getObject() throws Exception {



        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
