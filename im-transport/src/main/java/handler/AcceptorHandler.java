package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import protocol.MessageHolder;
import protocol.ProtocolHeader;
import queue.TaskQueue;

import java.util.concurrent.BlockingQueue;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 19:13
 **/
public class AcceptorHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(AcceptorHandler.class);

    private final BlockingQueue<MessageHolder> taskQueue;

    public AcceptorHandler() {
        taskQueue = TaskQueue.getQueue();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageHolder) {
            MessageHolder holder = (MessageHolder) msg;
            //指定channel
            holder.setChannel(ctx.channel());
            boolean offer = taskQueue.offer(holder);
            logger.info("TaskQueue添加任务: taskQueue=" + taskQueue.size());
            if (!offer) {
                //服务器繁忙
                logger.warn("服务器繁忙，拒绝提供服务");
                // 繁忙响应
                response(ctx.channel(), holder.getSign());
            }
        } else {
            throw new IllegalArgumentException("msg is not instance of MessageHolder");
        }
    }

    private void response(Channel channel, byte sign) {
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setSign(ProtocolHeader.RESPONSE);
        messageHolder.setType(sign);
        messageHolder.setStatus(ProtocolHeader.SERVER_BUSY);
        messageHolder.setBody("");
        channel.writeAndFlush(messageHolder);
    }
}
