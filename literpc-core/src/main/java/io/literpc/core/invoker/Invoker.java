package io.literpc.core.invoker;

import io.literpc.core.response.RpcResponse;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public interface Invoker<T> {

    RpcResponse invoke();

    URL getUrl();

    Class<T> getInterface();

    boolean isAvailable();

    void destroy();


}
