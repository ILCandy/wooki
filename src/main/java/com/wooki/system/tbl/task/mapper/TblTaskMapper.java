package com.wooki.system.tbl.task.mapper;

import com.wooki.system.tbl.task.entity.TblTask;
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
public interface TblTaskMapper extends BaseMapper<TblTask> {
    public Integer updateEnableBatch(@Param("ids") List ids, @Param("status") Integer status);
}