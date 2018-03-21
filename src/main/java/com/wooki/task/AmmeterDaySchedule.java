package com.wooki.task;

import com.wooki.task.thread.AmmeterDayThread;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/30
 * Time : 下午11:54
 */
@Component
public class AmmeterDaySchedule {

    /**
     * 每天 01:00 分计算前一天
     * 开启线程计算昨天的总电量
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void dayCalcu(){
        // 开启线程计算昨天的总电量
        new AmmeterDayThread().start();
    }
}
