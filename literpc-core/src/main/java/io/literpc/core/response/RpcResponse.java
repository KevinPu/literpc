package io.literpc.core.response;

import java.io.Serializable;

/**
 * @author kevin Pu
 */
public class RpcResponse implements Response, Serializable {

    private static final long serialVersionUID = 425218278602560000L;

    private Object value;

    private Exception exception;

    private String requestId;

    public Object getValue() {
        return value;
    }

    public RpcResponse(Object value, String requestId) {
        this.value = value;
        this.requestId = requestId;
        this.exception = null;
    }

    public RpcResponse(Object value, Exception exception, String requestId) {
        this.value = value;
        this.exception = exception;
        this.requestId = requestId;
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

    @Override
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
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
