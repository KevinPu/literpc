package io.literpc.core.response;

import java.util.concurrent.TimeUnit;

/**
 * @author kevin Pu
 */
public class RpcResponseFuture implements ResponseFuture {

    private FutureState state = new FutureState();

    private final Object lock = new Object();

    private Object value;

    private Exception exception;

    private String requestId;

    private final long createTime = System.currentTimeMillis();
    // 超时时长
    private int timeout = 3000;

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
        return state.isDone();
    }

    @Override
    public Object get() {

        if (state.isDoing()) {
            // 不处理超时的情况，将会一直等待
            if (timeout <= 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                long waitTime = timeout - (System.currentTimeMillis() - createTime);
                if (waitTime > 0) {
                    for (; ; ) {
                        try {
                            lock.wait(waitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (state.isDone()) {
                            break;
                        } else {
                            waitTime = timeout - (System.currentTimeMillis() - createTime);
                            if (waitTime <= 0) {
                                break;
                            }
                        }
                    }
                }
                if (state.isDoing()) {
                    throw new RuntimeException("请求超时");
                }
            }
        }

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
        synchronized (lock) {
            if (state.isDoing()) {
                state.setStatus(FutureState.DONE);
                lock.notifyAll();
            }
        }
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


    private class FutureState {
        private static final int DONE = 1;
        private static final int DOING = 0;

        private volatile int status = DOING;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        private boolean isDone() {
            return this.status == DONE;
        }

        private boolean isDoing() {
            return this.status == DOING;
        }
    }

/*    static class Sync extends AbstractQueuedSynchronizer {

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

    }*/
}
