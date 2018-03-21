package com.wooki.system.amme.info.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.amme.info.entity.AmmeterMonthInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-27
 */
public interface AmmeterMonthInfoService extends IService<AmmeterMonthInfo> {

    public List selectEveryMonthInfo(List uuids, Integer limit);

}
