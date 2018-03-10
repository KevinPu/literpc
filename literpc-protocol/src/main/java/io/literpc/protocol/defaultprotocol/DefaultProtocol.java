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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevin Pu
 */
public class DefaultProtocol implements Protocol {

    private final Map<String, Server> serverMap = new ConcurrentHashMap<String, Server>();

    private final Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();

    private static final Transporter transporter = new NettyTransporter();

    private MessageHandler handler = new MessageHandler() {
        @Override
        public Object handle(Channel channel, Object message) {
            return null;
        }
    };

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) {

        Exporter<T> exporter = new DefaultExPorter<T>(invoker);

        Server server = getServer(invoker.getUrl());
        return exporter;
    }


    private Server getServer(URL url) {

        String key = url.getAddress();

        Server server = serverMap.get(key);
        if (server != null) {
            return server;
        }
        server = transporter.createServer(url, handler);
        server.bind();

        serverMap.put(key, server);

        return server;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        Client client = getClient(url);

        return new DefaultRefererInvoker<T>(type, url, getClient(url));
    }

    private Client getClient(URL url) {
        Client client = transporter.createClient(url, handler);

        client.connect();

        return client;
    }
}
