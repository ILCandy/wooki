package com.wooki.system.tbl.task.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.task.dto.TaskDelDto;
import com.wooki.system.tbl.task.dto.TaskUpdateStatusDto;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
@TableName("tbl_device_task")
public class TblDeviceTask extends Model<TblDeviceTask> {

    private static final long serialVersionUID = 1L;

	public TblDeviceTask(){}
	public TblDeviceTask(TblDevice device){
		this.deviceId = device.getId();
	}
	public TblDeviceTask(TaskDelDto taskDelDto){
		this.taskId = taskDelDto.getTaskId();
	}
	public TblDeviceTask(TaskUpdateStatusDto taskUpdateStatusDto){
		this.id = taskUpdateStatusDto.getId();
		this.enable = Integer.valueOf(Constant.map.get(taskUpdateStatusDto.getStatus()));
	}

	private Long id;
	@TableField("device_id")
	private Integer deviceId;
    /**
     * 情景id
     */
	@TableField("task_id")
	private Long taskId;
    /**
     * 执行数据
     */
	private String data;
    /**
     * 星期几  23456 星期1 ~5 ，1 星期天
     */
	private Integer day;
    /**
     * 网关id，方便发送信息
     */
	@TableField("gateway_id")
	private Integer gatewayId;

	private Integer enable;

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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblDeviceTask{" +
			"id=" + id +
			", deviceId=" + deviceId +
			", taskId=" + taskId +
			", data=" + data +
			", day=" + day +
			", gatewayId=" + gatewayId +
			"}";
	}
}
