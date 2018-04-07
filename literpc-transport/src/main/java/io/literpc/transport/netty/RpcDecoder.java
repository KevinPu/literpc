package io.literpc.transport.netty;

import io.literpc.core.response.RpcResponse;
import io.literpc.core.serializer.KryoSerializer;
import io.literpc.core.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author kevin Pu
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Serializer serializer = new KryoSerializer();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int dataLength = in.readInt();

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        RpcResponse response = serializer.deserialize(data);

        out.add(response);
    }
}
