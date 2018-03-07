package io.literpc.core.exporter;

import io.literpc.core.invoker.Invoker;

/**
 * @author kevin Pu
 */
public class DefaultExPorter<T> implements Exporter<T> {

    private final Invoker<T> invoker;


    public DefaultExPorter(Invoker<T> invoker) {
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }

    @Override
    public void unexport() {
        this.invoker.destroy();
    }
}
