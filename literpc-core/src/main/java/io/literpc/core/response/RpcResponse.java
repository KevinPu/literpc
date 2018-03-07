package io.literpc.core.response;

import java.io.Serializable;

/**
 * @author kevin Pu
 */
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 425218278602560000L;

    private Object value;

    private Exception exception;

    private long requestId;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "value=" + value +
                ", exception=" + exception +
                ", requestId=" + requestId +
                '}';
    }
}
