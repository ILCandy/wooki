package com.wooki.system.tbl.scene.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.wooki.system.tbl.bindScene.entity.TblDeviceScene;
import com.wooki.system.tbl.scene.entity.TblScene;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whn
 * @since 2018-01-12
 */
public interface TblSceneService extends IService<TblScene> {
    public boolean deleteScene(EntityWrapper<TblDeviceScene> entityWrapper, TblTask task, TblDeviceTask deviceTask, TblScene scene);
}
