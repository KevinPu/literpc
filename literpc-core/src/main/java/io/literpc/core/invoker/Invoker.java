package io.literpc.core.invoker;

/**
 * @author kevin Pu
 */
public interface Invoker<T> {

    void invoke();

    Class<T> getInterface();

    boolean isAvailable();

    void destroy();


}
