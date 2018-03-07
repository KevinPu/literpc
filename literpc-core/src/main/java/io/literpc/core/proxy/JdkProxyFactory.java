package io.literpc.core.proxy;

import io.literpc.core.invoker.Invoker;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public class JdkProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Invoker<T> invoker) {
        return null;
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        return null;
    }
}
