package com.wooki.system.tbl.device.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wooki.system.tbl.device.entity.GroupOnline;
import com.wooki.system.tbl.device.entity.SceneOnline;
import com.wooki.system.tbl.device.entity.TblDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
public interface TblDeviceMapper extends BaseMapper<TblDevice> {
    List<TblDevice> selectDevices(@Param("gateWayList") List gateWayList);
    List<GroupOnline> selectGroupOnline(TblDevice device);
    List<SceneOnline> selectSceneOnline(@Param("device") TblDevice device,@Param("list") List list);

    TblDevice selectBySelectKey(TblDevice device);
    Integer updateBySelectKey(TblDevice device);

    Integer updateBatchDeviceGroup(@Param("groupId") Integer groupId,@Param("ids") List<Integer> ids);
}