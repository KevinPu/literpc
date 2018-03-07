package io.literpc.protocol;

import io.literpc.core.exporter.Exporter;
import io.literpc.core.invoker.Invoker;

/**
 * @author kevin Pu
 */
public interface Protocol {

    <T> Exporter<T> export(Invoker<T> invoker);

}
