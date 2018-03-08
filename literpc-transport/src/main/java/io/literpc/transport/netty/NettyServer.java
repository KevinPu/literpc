package io.literpc.transport.netty;

import io.literpc.core.url.URL;
import io.literpc.transport.Channel;
import io.literpc.transport.MessageHandler;
import io.literpc.transport.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author kevin Pu
 */
public class NettyServer implements Server, Channel {

    private URL url;

    private MessageHandler handler;

    public NettyServer(URL url, MessageHandler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void open() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast("handler",
                                    new NettyChannelHandler(NettyServer.this, handler));
                        }
                    });

            ChannelFuture f = bootstrap.bind(url.getPort()).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
