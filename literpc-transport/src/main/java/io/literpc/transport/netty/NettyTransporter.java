package io.literpc.transport.netty;

import io.literpc.core.url.URL;
import io.literpc.core.client.Client;
import io.literpc.core.handler.MessageHandler;
import io.literpc.core.server.Server;
import io.literpc.transport.Transporter;

/**
 * @author kevin Pu
 */
public class NettyTransporter implements Transporter {

    @Override
    public Server create(URL url, MessageHandler handler) {
        return new NettyServer(url, handler);
    }

    @Override
    public Client connect(URL url) {
        return null;
    }
}
