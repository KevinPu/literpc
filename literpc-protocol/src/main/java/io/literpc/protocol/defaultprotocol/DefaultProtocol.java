package io.literpc.protocol.defaultprotocol;

import io.literpc.core.exporter.DefaultExPorter;
import io.literpc.core.exporter.Exporter;
import io.literpc.core.invoker.Invoker;
import io.literpc.core.url.URL;
import io.literpc.protocol.Protocol;
import io.literpc.transport.Server;
import io.literpc.transport.Transporter;
import io.literpc.transport.netty.NettyTransporter;

/**
 * @author kevin Pu
 */
public class DefaultProtocol implements Protocol {

    private static final Transporter transporter = new NettyTransporter();

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) {

        Exporter<T> exporter = new DefaultExPorter<T>(invoker);
        openServer(invoker.getUrl());

        return exporter;
    }

    private void openServer(URL url) {

        Server server = transporter.bind(url);
    }
}
