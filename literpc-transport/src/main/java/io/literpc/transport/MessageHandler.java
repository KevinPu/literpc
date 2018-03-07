package io.literpc.transport;

/**
 * @author kevin Pu
 */
public interface MessageHandler {


    Object handle(Channel channel, Object message);

}
