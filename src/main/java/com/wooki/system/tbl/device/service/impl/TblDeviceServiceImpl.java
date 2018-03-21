package com.wooki.system.tbl.device.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.tbl.bindScene.entity.TblDeviceScene;
import com.wooki.system.tbl.bindScene.service.TblDeviceSceneService;
import com.wooki.system.tbl.device.entity.GroupOnline;
import com.wooki.system.tbl.device.entity.SceneOnline;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.mapper.TblDeviceMapper;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.scene.entity.TblScene;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@Service
public class TblDeviceServiceImpl extends ServiceImpl<TblDeviceMapper, TblDevice> implements TblDeviceService {

    @Autowired
    TblDeviceMapper deviceMapper;

    @Autowired
    TblDeviceTaskService deviceTaskService; // 设备定时

    @Autowired
    TblDeviceSceneService deviceSceneService;// 设备绑定情景


    @Override
    @Transactional
    public Boolean refreshDevice(TblDevice delDevice, List<TblDevice> list) {
        operaData(list);
//        insertBatch(list);
        return true;
    }

    @Transactional
    public boolean operaData(List<TblDevice> list){
        TblDevice checkDevice;
        List addList = new ArrayList();
        for(TblDevice device:list) {
            checkDevice = deviceMapper.selectBySelectKey(device);
            if(checkDevice==null) {
                addList.add(device);
            }
            else
                deviceMapper.updateBySelectKey(device);
        }
        if(addList.size()>0)
            insertBatch(addList);
        return true;
    }

    @Override
    public List<TblDevice> selectDevices(List gateWayList) {
        return deviceMapper.selectDevices(gateWayList);
    }

    @Override
    public boolean deviceAddTask(TblDeviceScene deviceScene, List list) {
        deviceTaskService.insertBatch(list);
        deviceSceneService.insert(deviceScene);
        return false;
    }

    @Override
    public List<GroupOnline> selectGroupOnline(TblDevice device) {
        return deviceMapper.selectGroupOnline(device);
    }

    @Override
    public List<SceneOnline> selectSceneOnline(TblDevice device, List<TblScene> sceneList) {
        return deviceMapper.selectSceneOnline(device,sceneList);
    }

    @Override
    public Integer updateBySelectKey(TblDevice device) {
        System.out.println("updateBySelectKey: "+device);
        return deviceMapper.updateBySelectKey(device);
    }

    @Override
    public Integer updateBatchDeviceGroup(Integer groupId, List<Integer> ids) {
        return deviceMapper.updateBatchDeviceGroup(groupId,ids);
    }
}
