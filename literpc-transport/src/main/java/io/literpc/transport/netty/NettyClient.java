package io.literpc.transport.netty;

import io.literpc.core.channel.Channel;
import io.literpc.core.client.Client;
import io.literpc.core.handler.MessageHandler;
import io.literpc.core.url.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author kevin Pu
 */
public class NettyClient implements Client, Channel {

    private final URL url;

    private final MessageHandler handler;

    public NettyClient(URL url, MessageHandler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast("handler", new NettyClientHandler(NettyClient.this, handler));
                        }
                    });
            ChannelFuture future = bootstrap.connect(url.getHost(), url.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
