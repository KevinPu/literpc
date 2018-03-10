package io.literpc.core.response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author kevin Pu
 */
public class RpcResponseFuture implements ResponseFuture {

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getRequestId() {
        return null;
    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onFailure(Response response) {

    }
}
