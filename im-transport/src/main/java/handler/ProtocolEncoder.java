package handler;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 19:07
 **/

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.log4j.Logger;
import protocol.MessageHolder;
import protocol.ProtocolHeader;

/**
 * 编码Handler.
 * <p>
 * im Protocol
 * __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __
 * |           |           |           |           |              |                          |
 * 2           1           1           1            4               Uncertainty
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 * |           |           |           |           |              |                          |
 * Magic        Sign        Type       Status     Body Length         Body Content
 * |__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __|__ __ __ __ __|__ __ __ __ __ __ __ __ __|
 * <p>
 * 协议头9个字节定长
 * Magic      // 数据包的验证位，short类型
 * Sign       // 消息标志，请求／响应／通知，byte类型
 * Type       // 消息类型，登录／发送消息等，byte类型
 * Status     // 响应状态，成功／失败，byte类型
 * BodyLength // 协议体长度，int类型
 **/
public class ProtocolEncoder extends MessageToByteEncoder<MessageHolder> {
    private static final Logger logger = Logger.getLogger(ProtocolDecoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageHolder messageHolder, ByteBuf byteBuf) throws Exception {
        String body = messageHolder.getBody();
        if (body == null) {
//            throw new NullParam
        }
        byte[] bytes = body.getBytes("utf-8");
        byteBuf.writeShort(ProtocolHeader.MAGIC)
                .writeByte(messageHolder.getSign())
                .writeByte(messageHolder.getType())
                .writeByte(messageHolder.getStatus())
                .writeInt(bytes.length)
                .writeBytes(bytes);
    }
}
