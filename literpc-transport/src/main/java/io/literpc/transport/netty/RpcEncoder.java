package io.literpc.transport.netty;

import io.literpc.core.serializer.KryoSerializer;
import io.literpc.core.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author kevin Pu
 */
public class RpcEncoder<I> extends MessageToByteEncoder<I> {

    private Serializer serializer = new KryoSerializer();


    @Override
    protected void encode(ChannelHandlerContext ctx, I msg, ByteBuf out) {
        byte[] bytes = serializer.serialize(msg);
        int length = bytes.length;
        out.writeInt(length);
        out.writeBytes(bytes);
    }
}
