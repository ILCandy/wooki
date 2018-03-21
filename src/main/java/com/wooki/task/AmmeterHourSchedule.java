package com.wooki.task;

import com.wooki.function.exception.CustomException;
import com.wooki.system.base.ReturnEnum;
import com.wooki.task.thread.AmmeterHourThread;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 下午11:59
 */
@Component
public class AmmeterHourSchedule {

    /**
     * 每小时 50 分 登录一次，计算电量
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 50 */1 * * ?")
    public void everyOneHour() {
        try {
            new AmmeterHourThread().start();
        }catch (CustomException e){
            throw new CustomException(ReturnEnum.THREAD_START_ERROR);
        }
    }

}
