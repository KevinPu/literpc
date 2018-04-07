package io.literpc.transport.netty;

import io.literpc.core.client.Client;
import io.literpc.core.request.Request;
import io.literpc.core.request.RpcRequest;
import io.literpc.core.response.Response;
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
public class NettyClient implements Client {

    private final URL url;

    private final NettyClientHandler handler = new NettyClientHandler();

    public NettyClient(URL url) {
        this.url = url;
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
                            ch.pipeline()
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcEncoder<RpcRequest>())
                                    .addLast("handler", handler);
                        }
                    });
            ChannelFuture future = bootstrap.connect(url.getHost(), url.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response request(Request request) {
        return handler.sendRequest(request);
    }
}
