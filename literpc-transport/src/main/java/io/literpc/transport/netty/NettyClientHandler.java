package io.literpc.transport.netty;

import io.literpc.core.channel.Channel;
import io.literpc.core.handler.MessageHandler;
import io.netty.channel.ChannelDuplexHandler;

/**
 * @author kevin Pu
 */
public class NettyClientHandler extends ChannelDuplexHandler {

    private final MessageHandler handler;

    private final Channel channel;

    public NettyClientHandler(Channel channel, MessageHandler handler) {
        this.handler = handler;
        this.channel = channel;
    }
}
