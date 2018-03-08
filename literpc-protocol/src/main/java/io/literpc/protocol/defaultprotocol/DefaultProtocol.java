package io.literpc.protocol.defaultprotocol;

import io.literpc.core.channel.Channel;
import io.literpc.core.client.Client;
import io.literpc.core.exporter.DefaultExPorter;
import io.literpc.core.exporter.Exporter;
import io.literpc.core.handler.MessageHandler;
import io.literpc.core.invoker.DefaultRefererInvoker;
import io.literpc.core.invoker.Invoker;
import io.literpc.core.server.Server;
import io.literpc.core.url.URL;
import io.literpc.protocol.Protocol;
import io.literpc.transport.Transporter;
import io.literpc.transport.netty.NettyTransporter;

/**
 * @author kevin Pu
 */
public class DefaultProtocol implements Protocol {

    private static final Transporter transporter = new NettyTransporter();

    private MessageHandler handler;

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) {

        initMessageHandler(invoker);

        Exporter<T> exporter = new DefaultExPorter<T>(invoker);

        openServer(invoker.getUrl());

        return exporter;
    }

    private <T> void initMessageHandler(Invoker<T> invoker) {
        handler = new MessageHandler() {
            @Override
            public Object handle(Channel channel, Object message) {
                return null;
            }
        };
    }

    private void openServer(URL url) {

        Server server = transporter.create(url, handler);
        server.open();
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        return new DefaultRefererInvoker<T>(type, url, getClient());
    }

    private Client getClient() {
        return null;
    }
}
