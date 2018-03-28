package io.literpc.transport.netty;

import io.literpc.core.channel.Channel;
import io.literpc.core.handler.MessageHandler;
import io.literpc.core.request.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author kevin Pu
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {

    private final MessageHandler handler;

    private final Channel channel;

    public NettyServerHandler(Channel channel, MessageHandler handler) {
        this.channel = channel;
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) {

        Object response = handler.handle(channel, request);

        ctx.writeAndFlush(response);
    }
}
