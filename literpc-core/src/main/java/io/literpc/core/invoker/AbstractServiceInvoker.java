package io.literpc.core.invoker;

import io.literpc.core.request.Request;
import io.literpc.core.response.Response;
import io.literpc.core.response.RpcResponse;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public abstract class AbstractServiceInvoker<T> implements Invoker<T> {

    private final T proxy;

    private final Class<T> type;

    private final URL url;

    public AbstractServiceInvoker(T proxy, Class<T> type, URL url) {
        this.proxy = proxy;
        this.type = type;
        this.url = url;
    }

    @Override
    public Response invoke(Request request) {
        try {
            return new RpcResponse(doInvoke(proxy, request.getMethodName(),
                    request.getParameterTypes(), request.getParameters()),
                    request.getRequestId());
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Exception;

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public Class<T> getInterface() {
        return this.type;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void destroy() {

    }
}
