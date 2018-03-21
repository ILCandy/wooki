package com.wooki.system.amme.info.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wooki.system.amme.info.entity.AmmeterCurrentMonth;
import com.wooki.system.amme.info.entity.AmmeterHourInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-02-02
 */
public interface AmmeterCurrentMonthMapper extends BaseMapper<AmmeterCurrentMonth> {

    Integer updateByUuid(Object o);
    Integer updateBatchByUuid(@Param("list") List<AmmeterHourInfo> list);
    Double currentMonthInfo(@Param("Uuids") List list);
    // 每月清零
    Integer setZero();
    public List selectEveryDevice(@Param("Uuids") List uuids);
}