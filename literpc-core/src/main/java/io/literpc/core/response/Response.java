package io.literpc.core.response;

/**
 * @author kevin Pu
 */
public interface Response {

    Object getValue();

    String getRequestId();

    Exception getException();
}
