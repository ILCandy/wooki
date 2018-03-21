package com.wooki.system.amme.device.service.impl;

import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.amme.device.mapper.AmmeterDeviceMapper;
import com.wooki.system.amme.device.service.AmmeterDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.tbl.company.entity.TblCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-26
 */
@Service
public class AmmeterDeviceServiceImpl extends ServiceImpl<AmmeterDeviceMapper, AmmeterDevice> implements AmmeterDeviceService {

    @Autowired
    AmmeterDeviceMapper deviceMapper;

    @Override
    public Integer updateByUuid(AmmeterDevice device) {
        return deviceMapper.updateByUuid(device);
    }

    @Override
    public AmmeterDevice selectByUuid(String uuid) {
        return deviceMapper.selectByUuid(uuid);
    }

    @Override
    public List typeAndAllpower(TblCompany company) {
        return deviceMapper.typeAndAllpower(company);
    }
}