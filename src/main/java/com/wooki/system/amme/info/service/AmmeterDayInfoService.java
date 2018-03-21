package com.wooki.system.amme.info.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.amme.info.entity.AmmeterDayInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-31
 */
public interface AmmeterDayInfoService extends IService<AmmeterDayInfo> {
    public void calcuLastdayInfo(EntityWrapper entityWrapper);
    public Double lastDayInfo(List Uuids,String timeStr);
    public List selectMonthInfo(List uuids,String timeStr);
}
