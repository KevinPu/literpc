package io.literpc.core.invoker;

import io.literpc.core.request.Request;
import io.literpc.core.response.Response;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public class AbstractRefererInvoker<T> implements Invoker<T> {

    @Override
    public Response invoke(Request request) {
        return null;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public Class<T> getInterface() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void destroy() {

    }
}
