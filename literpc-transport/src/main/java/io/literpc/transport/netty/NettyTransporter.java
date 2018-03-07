package io.literpc.transport.netty;

import io.literpc.core.url.URL;
import io.literpc.transport.Client;
import io.literpc.transport.MessageHandler;
import io.literpc.transport.Server;
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
