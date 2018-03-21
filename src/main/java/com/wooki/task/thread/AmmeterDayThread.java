package com.wooki.task.thread;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.amme.info.service.AmmeterDayInfoService;
import com.wooki.system.common.ApplicationContextUtil;
import com.wooki.task.TimeInfo;
import com.wooki.util.common.date.DateUtil;

import java.util.Date;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/30
 * Time : 下午11:58
 */
public class AmmeterDayThread extends Thread{

    private AmmeterDayInfoService ammeterDayInfoService;

    /**
     * 开启线程计算昨天的总电量
     */
    @Override
    public void run() {
        this.ammeterDayInfoService = (AmmeterDayInfoService)ApplicationContextUtil.getBean(AmmeterDayInfoService.class);
        System.out.println("1===================");
        // 更新时间
        TimeInfo.setTime();
        System.out.println("2===================");
        // 昨天0点
        Date lastDayTimeZero = DateUtil.getLastDayZero();
        // 今天0点
        Date currentDayTimeZero = DateUtil.getCurrentDayZero();
        // 时间条件
        EntityWrapper timeEW = new EntityWrapper();
        timeEW.between("time",lastDayTimeZero,currentDayTimeZero);
        System.out.println("3===================");
        // 计算昨天电量
        ammeterDayInfoService.calcuLastdayInfo(timeEW);
    }


}
