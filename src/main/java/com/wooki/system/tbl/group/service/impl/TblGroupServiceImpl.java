package com.wooki.system.tbl.group.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.group.entity.TblGroup;
import com.wooki.system.tbl.group.mapper.TblGroupMapper;
import com.wooki.system.tbl.group.service.TblGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-08
 */
@Service
public class TblGroupServiceImpl extends ServiceImpl<TblGroupMapper, TblGroup> implements TblGroupService {

    @Autowired
    TblGroupMapper groupMapper;

    @Autowired
    TblDeviceService deviceService;

    @Override
    @Transactional
    public boolean deleteGroup(TblGroup group) {

        // 删除分组
        groupMapper.deleteById(group);

        // 重置为无分组
        TblDevice updateDevice = new TblDevice();
        updateDevice.setGroupId(group.getId());

        TblDevice newDevice = new TblDevice();
        newDevice.setGroupId(0);

        // 清除设备的分组
        deviceService.update(newDevice,new EntityWrapper<>(updateDevice));

        return true;
    }
}
