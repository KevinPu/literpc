package io.literpc.core.invoker;

import io.literpc.core.client.Client;
import io.literpc.core.request.Request;
import io.literpc.core.response.Response;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public class DefaultRefererInvoker<T> extends AbstractRefererInvoker<T> {

    private final Client client;

    public DefaultRefererInvoker(Class<T> type, URL url, Client client) {
        super(type, url);
        this.client = client;
    }

    @Override
    protected Response doInvoke(Request request) {
        return client.request(request);
    }

}
