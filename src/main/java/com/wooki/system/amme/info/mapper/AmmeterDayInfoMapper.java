package com.wooki.system.amme.info.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wooki.system.amme.info.entity.AmmeterDayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-01-31
 */
public interface AmmeterDayInfoMapper extends BaseMapper<AmmeterDayInfo> {
    public Double lastDayInfo(@Param("Uuids") List uuids, @Param("timeStr") String timeStr);
    public List selectMonthInfo(@Param("Uuids")List uuids,@Param("timeStr")String timeStr);
}