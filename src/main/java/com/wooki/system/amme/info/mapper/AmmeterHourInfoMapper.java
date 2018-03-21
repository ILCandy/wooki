package com.wooki.system.amme.info.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.amme.info.entity.AmmeterHourInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-01-27
 */
public interface AmmeterHourInfoMapper extends BaseMapper<AmmeterHourInfo> {
    public List selectMonthSum(@Param("ew") EntityWrapper entityWrapper);
    public List selectDaySum(@Param("ew") EntityWrapper entityWrapper);
}