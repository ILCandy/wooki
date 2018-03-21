package com.wooki.system.tbl.lock.mapper;

import com.wooki.system.tbl.lock.entity.TblLockDevice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-02-28
 */
public interface TblLockDeviceMapper extends BaseMapper<TblLockDevice> {
    public Integer updateLockDevice(@Param("lockDevices")List<TblLockDevice> lockDeviceList);
}