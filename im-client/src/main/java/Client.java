import handler.ProtocolDecoder;
import handler.ProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 20:50
 **/
public class Client {
    public void connect(int port, String host) throws Exception {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new MyChannelHandler());
            // 异步链接服务器 同步等待链接成功
            ChannelFuture f = bootstrap.connect(host, port).sync();

            // 等待链接关闭
            f.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
            System.out.println("客户端优雅的释放了线程资源...");
        }
    }

    private class MyChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // 添加自定义协议的编解码工具
            ch.pipeline().addLast(new ProtocolDecoder());
            ch.pipeline().addLast(new ProtocolEncoder());
            // 处理网络IO
            ch.pipeline().addLast(new ClientHandler());
        }

    }

    public static void main(String[] args) throws Exception {
        new Client().connect(20000, "127.0.0.1");
    }
}
