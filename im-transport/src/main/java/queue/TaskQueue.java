package queue;

import protocol.MessageHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 19:15
 **/
public class TaskQueue {
    private volatile static BlockingQueue<MessageHolder> queue;

    public static BlockingQueue<MessageHolder> getQueue() {
        if (queue == null) {
            synchronized (TaskQueue.class) {
                if (queue == null) {
                    queue = new LinkedBlockingDeque<>(1024);
                }
            }
        }
        return queue;
    }
}
