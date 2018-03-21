package com.wooki.system.amme.info.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.amme.info.entity.AmmeterCurrentMonth;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-02-02
 */
public interface AmmeterCurrentMonthService extends IService<AmmeterCurrentMonth> {
    public boolean updateAmmeterCurrentMonth(List list);
    public Double currentMonthInfo(List uuids);
    public Integer setZero();
    public List selectEveryDevice(List uuids);

}
