package handler;

import connection.ConnPool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;
import protocol.MessageHolder;
import protocol.ProtocolHeader;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 19:24
 **/
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(HeartbeatHandler.class);
    public static AtomicBoolean isLogout = new AtomicBoolean(false);
    private Channel channel;
    private String username;
    //丢失的心跳数
    private int counter = 0;

    public HeartbeatHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (username == null) {
                username = ConnPool.query(channel);
            }
            //心跳丢失
            logger.info(username + "丢失" + counter + "个心跳包");
            if (counter > 4) {
                ctx.channel().close();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnPool.remove(username);
        if (isLogout.get()) {
            isLogout.set(false);
            logger.info(username + "退出登录");
        } else {
            logger.info(username + "与服务器断开");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageHolder) {
            MessageHolder messageHolder = (MessageHolder) msg;
            if (messageHolder.getType() == ProtocolHeader.HEARTBEAT) {
                if (username == null) {
                    username = ConnPool.query(channel);
                }
                //心跳清零
                counter = 0;
                ReferenceCountUtil.release(msg);
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }
}
