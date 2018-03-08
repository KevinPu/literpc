package io.literpc.core.proxy;

import io.literpc.core.invoker.AbstractServiceInvoker;
import io.literpc.core.invoker.Invoker;
import io.literpc.core.invoker.InvokerInvocationHandler;
import io.literpc.core.url.URL;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author kevin Pu
 */
public class JdkProxyFactory implements ProxyFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Invoker<T> invoker) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{invoker.getInterface()}, new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        return new AbstractServiceInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Exception {
                Method method = proxy.getClass().getMethod(methodName, parameterTypes);
                return method.invoke(proxy, parameters);
            }
        };
    }
}
