package com.wooki.system.tbl.task.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.mapper.TblDeviceTaskMapper;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
@Service
public class TblDeviceTaskServiceImpl extends ServiceImpl<TblDeviceTaskMapper, TblDeviceTask> implements TblDeviceTaskService {

    @Autowired
    TblDeviceTaskMapper deviceTaskMapper;

    @Override
    public Integer updateEnableBatch(List ids, Integer status) {
        return deviceTaskMapper.updateEnableBatch(ids,status);
    }

    @Override
    public Integer updateStatusByTaskId(TblDeviceTask deviceTask) {
        return deviceTaskMapper.updateStatusByTaskId(deviceTask);
    }
}
