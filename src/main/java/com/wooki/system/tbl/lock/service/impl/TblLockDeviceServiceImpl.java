package com.wooki.system.tbl.lock.service.impl;

import com.wooki.system.tbl.lock.entity.TblLockDevice;
import com.wooki.system.tbl.lock.mapper.TblLockDeviceMapper;
import com.wooki.system.tbl.lock.service.TblLockDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-02-28
 */
@Service
public class TblLockDeviceServiceImpl extends ServiceImpl<TblLockDeviceMapper, TblLockDevice> implements TblLockDeviceService {

    @Autowired
    TblLockDeviceMapper lockDeviceMapper;

    @Override
    public boolean updateLockDevice(List<TblLockDevice> lockDeviceList) {
        lockDeviceMapper.updateLockDevice(lockDeviceList);
        return true;
    }


}
