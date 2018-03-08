package io.literpc.core.invoker;

import io.literpc.core.request.Request;
import io.literpc.core.response.Response;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public interface Invoker<T> {

    Response invoke(Request request);

    URL getUrl();

    Class<T> getInterface();

    boolean isAvailable();

    void destroy();


}
