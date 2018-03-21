package com.wooki.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.amme.expand.service.DeviceExpandService;
import com.wooki.system.amme.info.entity.AmmeterMonthInfo;
import com.wooki.system.amme.info.service.AmmeterCurrentMonthService;
import com.wooki.system.amme.info.service.AmmeterHourInfoService;
import com.wooki.system.amme.info.service.AmmeterMonthInfoService;
import com.wooki.util.common.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 下午11:59
 */
@Component
public class AmmeterMonthSchedule {

    @Autowired
    DeviceExpandService deviceExpandService;

    @Autowired
    AmmeterHourInfoService ammeterHourInfoService;

    @Autowired
    AmmeterMonthInfoService ammeterMonthInfoService;

    @Autowired
    AmmeterCurrentMonthService currentMonthService;

    /**
     * 每月一号计算一次上个月的总量
     * 00:30
     * @throws Exception
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void everyMonth()throws Exception {
        Date lastMonthDay1 = DateUtil.getLastMonthDay1TimeZero();
        Date currentMonthDay1 = DateUtil.getCurrentMonthDay1TimeZero();

        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.between("time",lastMonthDay1,currentMonthDay1);

//        String dateStr = DateUtil.dateToStr(DateUtil.getLastMonthDay1TimeZero());
        Date date = DateUtil.getLastMonthDay1TimeZero();
        List<AmmeterMonthInfo> monthInfoList = ammeterHourInfoService.selectMonthSum(entityWrapper);
        if(monthInfoList!=null&&monthInfoList.size()>0) {
            for(int i = 0;i<monthInfoList.size();i++)
                monthInfoList.get(i).setTime(date);
//            Date date = new Date();
//            AmmeterMonthInfo ammeterMonthInfo;
//            for (int i = 0; i < monthInfoList.size(); i++) {
//                ammeterMonthInfo = monthInfoList.get(i);
//                ammeterMonthInfo.setTime(date);
//            }
            // 批量插入上个月的电表信息
            ammeterMonthInfoService.insertBatch(monthInfoList);
            currentMonthService.setZero();
        }
    }
}
