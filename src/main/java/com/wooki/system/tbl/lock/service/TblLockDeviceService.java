package com.wooki.system.tbl.lock.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.tbl.lock.entity.TblLockDevice;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-02-28
 */
public interface TblLockDeviceService extends IService<TblLockDevice> {
	public boolean updateLockDevice(List<TblLockDevice> lockDeviceList);
}
