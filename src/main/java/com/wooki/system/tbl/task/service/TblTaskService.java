package com.wooki.system.tbl.task.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
public interface TblTaskService extends IService<TblTask> {
	public boolean deleteTask(TblTask task, TblDeviceTask deviceTask);
	public boolean updateStatus(TblTask task, TblDeviceTask deviceTask);
	public Integer updateEnableBatch(@Param("ids") List ids, @Param("status") Integer status);
}
