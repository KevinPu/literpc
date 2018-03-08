package io.literpc.core.request;

/**
 * @author kevin Pu
 */
public interface Request {

    String getRequestId();

    String getInterfaceName();

    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getParameters();
}
