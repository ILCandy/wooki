package com.wooki.system.tbl.task.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.tbl.task.entity.TblDeviceTask;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
public interface TblDeviceTaskService extends IService<TblDeviceTask> {
    public Integer updateEnableBatch(List ids, Integer status);
    public Integer updateStatusByTaskId(TblDeviceTask deviceTask);
}
