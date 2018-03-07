package io.literpc.transport.netty;

import io.literpc.core.url.URL;
import io.literpc.transport.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author kevin Pu
 */
public class NettyServer implements Server {

    public NettyServer(URL url) {
        bind(url.getPort());
    }

    @Override
    public void bind(int port) {
        EventLoopGroup  bossGroup = new NioEventLoopGroup();
        EventLoopGroup  workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap  bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024);

            ChannelFuture f = bootstrap.bind(port).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
