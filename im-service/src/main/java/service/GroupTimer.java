package service;

import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
/**
 * @program: netty-im
 * @description:
 * @author: Yichao Chen
 * @create: 2018-12-06 20:08
 **/
public class GroupTimer {
    private static final Logger logger = Logger.getLogger(GroupTimer.class);

    public GroupTimer() {
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new GroupTimerTask(), 0, 120000);
    }

    class GroupTimerTask extends TimerTask {

        public void run() {
            logger.info("执行定时任务");
            logger.info("内存 Groups = " + GroupManager.getGroups().size() + " GroupTimes = " + GroupManager.getGroupTimes().size());
            Set<Map.Entry<String, Long>> entrySet = GroupManager.getGroupTimesIte();
            if (entrySet.size() > 0) {
                Iterator<Map.Entry<String, Long>> ite = entrySet.iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, Long> entry = ite.next();
                    String groupName = entry.getKey();
                    Long time = entry.getValue();
                    if ((System.currentTimeMillis() - time) > 300000) {
                        // 调出内存
                        GroupManager.groupTimesRemove(groupName);
                        GroupManager.groupRemove(groupName);
                        logger.info("讨论组<" + groupName + "> 已被调出内存" );
                    }
                }
            }
            logger.info("内存 Groups = " + GroupManager.getGroups().size() + " GroupTimes = " + GroupManager.getGroupTimes().size());
        }
    }
}
