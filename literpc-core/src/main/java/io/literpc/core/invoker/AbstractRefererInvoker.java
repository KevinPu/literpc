package io.literpc.core.invoker;

import io.literpc.core.request.Request;
import io.literpc.core.response.Response;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public abstract class AbstractRefererInvoker<T> implements Invoker<T> {

    private final Class<T> type;

    private final URL url;

    public AbstractRefererInvoker(Class<T> type, URL url) {
        this.type = type;
        this.url = url;
    }

    @Override
    public Response invoke(Request request) {
        return doInvoke(request);
    }

    protected abstract Response doInvoke(Request request);


    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void destroy() {

    }
}
