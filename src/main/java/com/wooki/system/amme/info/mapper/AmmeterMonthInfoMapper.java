package com.wooki.system.amme.info.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wooki.system.amme.info.entity.AmmeterMonthInfo;
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
public interface AmmeterMonthInfoMapper extends BaseMapper<AmmeterMonthInfo> {
    List selectEveryMonthInfo(@Param("Uuids") List uuids, @Param("limit") Integer limit);
}