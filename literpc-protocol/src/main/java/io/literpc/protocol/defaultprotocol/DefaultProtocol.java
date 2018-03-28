package io.literpc.protocol.defaultprotocol;

import io.literpc.core.client.Client;
import io.literpc.core.exporter.DefaultExPorter;
import io.literpc.core.exporter.Exporter;
import io.literpc.core.handler.MessageHandler;
import io.literpc.core.invoker.DefaultRefererInvoker;
import io.literpc.core.invoker.Invoker;
import io.literpc.core.request.Request;
import io.literpc.core.server.Server;
import io.literpc.core.url.URL;
import io.literpc.protocol.Protocol;
import io.literpc.transport.Transporter;
import io.literpc.transport.netty.NettyTransporter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevin Pu
 */
public class DefaultProtocol implements Protocol {

    private final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<>();

    private final Map<String, Server> serverMap = new ConcurrentHashMap<>();

    private final Map<String, Client> clientMap = new ConcurrentHashMap<>();

    private static final Transporter transporter = new NettyTransporter();

    private MessageHandler handler = (channel, message) -> {
        if (message instanceof Request) {
            Request request = (Request) message;
            String key = "";
            Exporter exporter = exporterMap.get(key);
            return exporter.getInvoker().invoke(request);
        }
        return null;
    };

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) {

        Exporter<T> exporter = new DefaultExPorter<>(invoker);

        exporterMap.put(invoker.getUrl().getAddress(), exporter);

        openServer(invoker.getUrl());
        return exporter;
    }


    private void openServer(URL url) {

        String key = url.getAddress();

        Server server = serverMap.get(key);
        if (server == null) {
            server = transporter.createServer(url, handler);
            serverMap.put(key, server);
            server.bind();
        }
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        Client client = getClient(url);

        return new DefaultRefererInvoker<>(type, url, getClient(url));
    }

    private Client getClient(URL url) {
        Client client = transporter.createClient(url, handler);

        client.connect();

        return client;
    }
}
