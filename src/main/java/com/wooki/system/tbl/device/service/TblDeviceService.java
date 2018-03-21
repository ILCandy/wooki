package com.wooki.system.tbl.device.service;

import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.tbl.bindScene.entity.TblDeviceScene;
import com.wooki.system.tbl.device.entity.GroupOnline;
import com.wooki.system.tbl.device.entity.SceneOnline;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.scene.entity.TblScene;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
public interface TblDeviceService extends IService<TblDevice> {
    Boolean refreshDevice(TblDevice delDevice,List<TblDevice> list);
    List <TblDevice> selectDevices(List gateWayList);
    public boolean deviceAddTask(TblDeviceScene deviceScene, List list);
    List <GroupOnline> selectGroupOnline(TblDevice device);
    List <SceneOnline> selectSceneOnline(TblDevice device, List<TblScene> sceneList);
    public Integer updateBySelectKey(TblDevice device);
    public Integer updateBatchDeviceGroup(Integer groupId,List<Integer> ids);
}
