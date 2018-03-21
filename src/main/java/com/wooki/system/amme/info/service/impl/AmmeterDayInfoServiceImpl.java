package com.wooki.system.amme.info.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.amme.info.entity.AmmeterDayInfo;
import com.wooki.system.amme.info.mapper.AmmeterDayInfoMapper;
import com.wooki.system.amme.info.service.AmmeterDayInfoService;
import com.wooki.system.amme.info.service.AmmeterHourInfoService;
import com.wooki.task.TimeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-31
 */
@Service
public class AmmeterDayInfoServiceImpl extends ServiceImpl<AmmeterDayInfoMapper, AmmeterDayInfo> implements AmmeterDayInfoService {

    @Autowired
    AmmeterHourInfoService ammeterHourInfoService;

    @Autowired
    AmmeterDayInfoMapper ammeterDayInfoMapper;

    @Override
    @Transactional
    public void calcuLastdayInfo(EntityWrapper entityWrapper) {
        // 计算昨天的电表信息11
        List<AmmeterDayInfo> dayInfos = ammeterHourInfoService.selectDaySum(entityWrapper);
        for(AmmeterDayInfo dayInfo:dayInfos){
            dayInfo.setTime(TimeInfo.getLastTimeStr());
        }
        if(dayInfos!=null&&dayInfos.size()>0) {
            // 插入每日电量表
            insertBatch(dayInfos);
        }
    }


    @Override
    public Double lastDayInfo(List Uuids, String timeStr) {
        return ammeterDayInfoMapper.lastDayInfo(Uuids,timeStr);
    }

    @Override
    public List selectMonthInfo(List uuids,String timeStr){
        return ammeterDayInfoMapper.selectMonthInfo(uuids,timeStr);
    }


}
