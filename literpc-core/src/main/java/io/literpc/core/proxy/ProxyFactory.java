package io.literpc.core.proxy;

import io.literpc.core.invoker.Invoker;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker);

    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url);
}
