package io.literpc.core.response;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author kevin Pu
 */
public class RpcResponseFuture implements ResponseFuture {

    private Sync sync;

    private Object value;

    private Exception exception;

    private String requestId;

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
        return sync.isDone();
    }

    @Override
    public Object get() {
        sync.acquire(-1);
        return this.value;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public Object getValue() {
        return get();
    }

    @Override
    public String getRequestId() {
        return this.requestId;
    }

    @Override
    public Exception getException() {
        return this.exception;
    }

    private void done() {
        sync.release(1);
    }

    @Override
    public void onSuccess(Response response) {
        this.requestId = response.getRequestId();
        this.value = response.getValue();
        done();

    }

    @Override
    public void onFailure(Response response) {
        this.requestId = response.getRequestId();
        this.exception = response.getException();
        done();
    }

    static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = -2746344557811998333L;

        private final int DONE = 1;
        private final int DOING = 0;

        @Override
        protected boolean tryAcquire(int arg) {
            return getState() == DONE;
        }

        @Override
        protected boolean tryRelease(int arg) {
            return getState() == DOING && compareAndSetState(DOING, DONE);
        }

        private boolean isDone() {
            return getState() == DONE;
        }

    }
}
