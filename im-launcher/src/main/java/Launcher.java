import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.NettyConfig;
import netty.NettyConfigImpl;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 16:37
 **/
public class Launcher {
    public static void main(String[] args) throws InterruptedException {
        start();
    }

    public static void init(){

    }
    public static void start() throws InterruptedException {

        // 启动服务
        new service.Service().initAndStart();

        NettyConfig config = new NettyConfigImpl();
        config.setParentGroup(1);
        config.setChildGroup();
        config.setChannel(NioServerSocketChannel.class);
        config.setHandler();
        config.bind(20000);
    }
}
