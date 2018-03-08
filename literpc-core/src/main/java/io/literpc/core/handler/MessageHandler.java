package io.literpc.core.handler;

import io.literpc.core.channel.Channel;

/**
 * @author kevin Pu
 */
public interface MessageHandler {


    Object handle(Channel channel, Object message);

}
