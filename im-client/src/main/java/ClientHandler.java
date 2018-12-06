import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;
import protocol.MessageHolder;
import protocol.ProtocolHeader;


/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 20:59
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setType(ProtocolHeader.REGISTER);
        messageHolder.setSign(ProtocolHeader.REQUEST);
        messageHolder.setChannel(ctx.channel());
        String body="{" +
                "username:cyc;" +
                "password:12345" +
                "}";
//        byte[] bytes=body.getBytes("utf-8");
//
        messageHolder.setBody(body);
        ctx.writeAndFlush(messageHolder);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            MessageHolder messageHolder = (MessageHolder) msg;
            logger.info("from server" + messageHolder.getBody());
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
