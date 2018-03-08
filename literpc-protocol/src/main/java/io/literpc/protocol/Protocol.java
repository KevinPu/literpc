package io.literpc.protocol;

import io.literpc.core.exporter.Exporter;
import io.literpc.core.invoker.Invoker;
import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public interface Protocol {

    <T> Exporter<T> export(Invoker<T> invoker);

    <T> Invoker<T> refer(Class<T> type, URL url);
}
