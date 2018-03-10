package io.literpc.core.client;

import io.literpc.core.request.Request;
import io.literpc.core.response.Response;

/**
 * @author kevin Pu
 */
public interface Client {

    void connect();

    Response request(Request request);
}
