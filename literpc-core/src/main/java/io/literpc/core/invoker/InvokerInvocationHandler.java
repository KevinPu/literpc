package io.literpc.core.invoker;

import io.literpc.core.request.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author kevin Pu
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private final Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return invoker.invoke(new RpcRequest("", invoker.getInterface().getName(), method.getName(), method.getParameterTypes(), args));
    }
}
