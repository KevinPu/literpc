package io.literpc.transport.netty;

import io.literpc.core.request.Request;
import io.literpc.core.response.Response;
import io.literpc.core.response.ResponseFuture;
import io.literpc.core.response.RpcResponseFuture;
import io.netty.channel.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author kevin Pu
 */
public class NettyClientHandler extends ChannelDuplexHandler {

    private volatile Channel channel;

    private final Map<String, ResponseFuture> responseFutureMap = new ConcurrentHashMap<>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Response) {
            Response response = (Response) msg;
            ResponseFuture responseFuture = responseFutureMap.remove(response.getRequestId());
            responseFuture.onSuccess(response);
        }
    }

    public Response sendRequest(Request request) {
        final CountDownLatch latch = new CountDownLatch(1);
        ResponseFuture response = new RpcResponseFuture();
        responseFutureMap.put(request.getRequestId(), response);
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

}
