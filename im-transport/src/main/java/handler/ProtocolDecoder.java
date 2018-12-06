package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.log4j.Logger;
import protocol.MessageHolder;
import protocol.ProtocolHeader;

import java.util.List;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 18:58
 **/

/**
 * 解码Handler.
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
public class ProtocolDecoder extends ByteToMessageDecoder {

    private static final Logger logger = Logger.getLogger(ProtocolDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        if (in.readableBytes() < ProtocolHeader.HEADER_LENGTH) {
            logger.info("数据包长度小于协议头长度");
            return;
        }
        in.markReaderIndex();
        if (in.readShort() != ProtocolHeader.MAGIC) {
            //不是自己的数据
            logger.info("Magic 不一致");
            in.resetReaderIndex();
            return;
        }
        //开始解码
        byte sign = in.readByte();
        byte type = in.readByte();
        byte status = in.readByte();
        int bodyLength = in.readInt();
        if (in.readableBytes() != bodyLength) {
            logger.info("消息体长度不一致");
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[bodyLength];
        in.readBytes(bytes);
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setSign(sign);
        messageHolder.setType(type);
        messageHolder.setStatus(status);
        messageHolder.setBody(new String(bytes, "utf-8"));

        list.add(messageHolder);
    }
}
