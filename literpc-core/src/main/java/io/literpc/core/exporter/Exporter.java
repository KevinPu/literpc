package io.literpc.core.exporter;

import io.literpc.core.invoker.Invoker;

/**
 * @author kevin Pu
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();

    void unexport();
}
