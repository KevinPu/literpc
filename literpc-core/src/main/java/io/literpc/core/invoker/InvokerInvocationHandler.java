package io.literpc.core.invoker;

import io.literpc.core.request.RpcRequest;
import io.literpc.core.response.Response;

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
        Response response = invoker.invoke(new RpcRequest("", invoker.getInterface().getName(), method.getName(), method.getParameterTypes(), args));
        // 返回真实的调用结果，如果没返回将阻塞
        return response.getValue();
    }
}
