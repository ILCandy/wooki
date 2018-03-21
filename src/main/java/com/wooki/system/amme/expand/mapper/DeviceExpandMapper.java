package com.wooki.system.amme.expand.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wooki.system.amme.expand.entity.DeviceExpand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-01-25
 */
public interface DeviceExpandMapper extends BaseMapper<DeviceExpand> {
    void updateByUuidBatch(@Param("expandList") List<DeviceExpand> list);
    void replaceBatchByUuid(@Param("expandList") List<DeviceExpand> list);
    String selectByUuid(@Param("Uuid")String uuid);
    Integer updateByUuid(DeviceExpand deviceExpand);

}