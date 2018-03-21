package com.wooki.system.tbl.task.mapper;

import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
public interface TblDeviceTaskMapper extends BaseMapper<TblDeviceTask> {
    public Integer updateEnableBatch(@Param("ids") List ids, @Param("status") Integer status);
    public Integer updateStatusByTaskId(TblDeviceTask deviceTask);
}