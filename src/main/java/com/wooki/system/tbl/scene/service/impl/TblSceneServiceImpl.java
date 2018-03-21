package com.wooki.system.tbl.scene.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wooki.system.tbl.bindScene.entity.TblDeviceScene;
import com.wooki.system.tbl.bindScene.service.TblDeviceSceneService;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.scene.entity.TblScene;
import com.wooki.system.tbl.scene.mapper.TblSceneMapper;
import com.wooki.system.tbl.scene.service.TblSceneService;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import com.wooki.system.tbl.task.service.TblTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whn
 * @since 2018-01-12
 */
@Service
public class TblSceneServiceImpl extends ServiceImpl<TblSceneMapper, TblScene> implements TblSceneService {

    @Autowired
    TblSceneService sceneService;

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    TblTaskService taskService;

    @Autowired
    TblDeviceTaskService deviceTaskService;

    @Autowired
    TblDeviceSceneService deviceSceneService;

    @Override
    @Transactional
    public boolean deleteScene(EntityWrapper<TblDeviceScene> entityWrapper, TblTask task, TblDeviceTask deviceTask, TblScene scene) {
        // 将设备的情景id设为空
//        deviceService.update(device,new EntityWrapper<>(checkDevice));
        deviceSceneService.delete(entityWrapper);
        taskService.deleteTask(task,deviceTask);
        // 删除情景
        sceneService.deleteById(scene);
        return true;
    }
}
