package com.wooki.system.tbl.bindScene.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-02-04
 */
@TableName("tbl_device_scene")
public class TblDeviceScene extends Model<TblDeviceScene> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 设备id
     */
	@TableField("device_id")
	private Integer deviceId;
    /**
     * 情景id
     */
	@TableField("scene_id")
	private Integer sceneId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblDeviceScene{" +
			"id=" + id +
			", deviceId=" + deviceId +
			", sceneId=" + sceneId +
			"}";
	}
}
