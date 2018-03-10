package io.literpc.core.response;

import java.util.concurrent.Future;

/**
 * @author kevin Pu
 */
public interface ResponseFuture extends Future, Response {
    void onSuccess(Response response);

    void onFailure(Response response) ;


}
