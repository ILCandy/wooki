package com.wooki.system.amme.device.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.tbl.company.entity.TblCompany;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-26
 */
public interface AmmeterDeviceService extends IService<AmmeterDevice> {
	public Integer updateByUuid(AmmeterDevice device);
	public AmmeterDevice selectByUuid(String uuid);
	public List typeAndAllpower(TblCompany company);
}