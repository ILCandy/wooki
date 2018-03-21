package com.wooki.system.tbl.task.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;
import com.wooki.system.tbl.task.mapper.TblTaskMapper;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import com.wooki.system.tbl.task.service.TblTaskService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class TblTaskServiceImpl extends ServiceImpl<TblTaskMapper, TblTask> implements TblTaskService {

    @Autowired
    TblTaskMapper taskMapper;

    @Autowired
    TblDeviceTaskService deviceTaskService;

    @Override
    @Transactional
    public boolean deleteTask(TblTask task, TblDeviceTask deviceTask) {
        // 删除定时表
        if(task!=null)
            taskMapper.delete(new EntityWrapper<>(task));
        // 删除定时-设备表
        if(deviceTask!=null)
            deviceTaskService.delete(new EntityWrapper<>(deviceTask));
        return true;
    }

    // 根据定时 id 更新定时状态
    @Override
    @Transactional
    public boolean updateStatus(TblTask task, TblDeviceTask deviceTask) {
        this.updateById(task);
        deviceTaskService.updateStatusByTaskId(deviceTask);
        return true;
    }

    // 根据id 列表更新任务状态
    @Override
    public Integer updateEnableBatch(@Param("ids") List ids, @Param("status") Integer status) {
        return taskMapper.updateEnableBatch(ids,status);
    }
}
