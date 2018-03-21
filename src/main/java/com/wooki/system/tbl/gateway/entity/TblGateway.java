package com.wooki.system.tbl.gateway.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2017-12-31
 */
@TableName("tbl_gateway")
public class TblGateway extends Model<TblGateway> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("gateway_id")
	private Integer gatewayId;
	private String password;
	private String version;
	private String snid;
	@TableField("device_count")
	private Integer deviceCount;
	@TableField("group_count")
	private Integer groupCount;
	@TableField("job_count")
	private Integer jobCount;
	@TableField("scene_count")
	private Integer sceneCount;
	@TableField("task_count")
	private Integer taskCount;
	@TableField("update_time")
	private Date updateTime;
	@TableField("add_time")
	private Date addTime;
	private String data;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSnid() {
		return snid;
	}

	public void setSnid(String snid) {
		this.snid = snid;
	}

	public Integer getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Integer deviceCount) {
		this.deviceCount = deviceCount;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	public Integer getJobCount() {
		return jobCount;
	}

	public void setJobCount(Integer jobCount) {
		this.jobCount = jobCount;
	}

	public Integer getSceneCount() {
		return sceneCount;
	}

	public void setSceneCount(Integer sceneCount) {
		this.sceneCount = sceneCount;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblGateway{" +
			"id=" + id +
			", gatewayId=" + gatewayId +
			", password=" + password +
			", version=" + version +
			", snid=" + snid +
			", deviceCount=" + deviceCount +
			", groupCount=" + groupCount +
			", jobCount=" + jobCount +
			", sceneCount=" + sceneCount +
			", taskCount=" + taskCount +
			", updateTime=" + updateTime +
			", addTime=" + addTime +
			", data=" + data +
			"}";
	}
}
