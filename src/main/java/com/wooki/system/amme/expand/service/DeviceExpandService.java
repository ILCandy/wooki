package com.wooki.system.amme.expand.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.amme.expand.entity.DeviceExpand;
import com.wooki.system.amme.info.entity.AmmeterHourInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-25
 */
public interface DeviceExpandService extends IService<DeviceExpand> {
	boolean updateAmmeterInfo(List<AmmeterDevice> deviceList,List<DeviceExpand> expandList, List<AmmeterHourInfo> resultList);
	boolean updateByUuidBatch(List<DeviceExpand> list);
}
