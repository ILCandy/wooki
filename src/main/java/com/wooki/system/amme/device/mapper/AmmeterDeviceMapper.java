package com.wooki.system.amme.device.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.tbl.company.entity.TblCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2018-01-26
 */
public interface AmmeterDeviceMapper extends BaseMapper<AmmeterDevice> {
//    Integer replaceAmmeterDevice(AmmeterDevice ammeterDevice);
    public Integer updateByUuid(AmmeterDevice device);
    public Integer checkUuid(@Param("Uuid") String Uuid);
    public AmmeterDevice selectByUuid(@Param("Uuid") String Uuid);

    public void replaceBatchByUuid(@Param("deviceList") List<AmmeterDevice> list);

    public List typeAndAllpower(TblCompany company);

}