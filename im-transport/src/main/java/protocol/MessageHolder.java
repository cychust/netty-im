package protocol;


import io.netty.channel.Channel;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 18:49
 **/
public class MessageHolder {
    //消息标志
    private byte sign;
    //    消息类型
    private byte type;
    //响应类型
    private byte status;
    //Json 消息体
    private String body;

    //接收消息的通道
    private Channel channel;

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MessageHolder{" +
                "sign=" + sign +
                ", type=" + type +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", channel=" + channel +
                '}';
    }
}
