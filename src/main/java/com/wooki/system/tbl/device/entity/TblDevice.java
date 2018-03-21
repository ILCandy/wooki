package com.wooki.system.tbl.device.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.wooki.system.tbl.group.dto.DeviceUpdateDto;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.analy.TestReceive;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@TableName("tbl_device")
public class TblDevice extends Model<TblDevice> {

    private static final long serialVersionUID = 1L;

	public TblDevice(){}
	public TblDevice(TestReceive testReceive){
		this.gatewayId = testReceive.getGatewayId();
		this.data = testReceive.getData();
	}
	public TblDevice(DeviceUpdateDto deviceUpdateDto){
		this.id = deviceUpdateDto.getId();
		this.name = deviceUpdateDto.getName();
	}
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("gateway_id")
	private Integer gatewayId;
	private String name;
	@TableField("short_addr")
	private String shortAddr;
	@TableField("end_point")
	private String endPoint;
	@TableField("profile_id")
	private String profileId;
	@TableField("device_id")
	private String deviceId;
	@TableField("switch_status")
	private Integer switchStatus;
	private Integer online;
	private String ieee;
	private String sn;
	@TableField("zone_type")
	private String zoneType;
	@TableField("battery_power")
	private String batteryPower;
	@TableField("update_time")
	private Date updateTime;
	@TableField("add_time")
	private Date addTime;
	private String data;
	@TableField("group_id")
	private Integer groupId;
	@TableField("company_id")
	private Integer companyId;
	@TableField("select_key")
	private String selectKey;
	@TableField(exist = false)
	private String groupName;
	@TableField(exist = false)
	private List<TblDeviceTask> tasks;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortAddr() {
		return shortAddr;
	}

	public void setShortAddr(String shortAddr) {
		this.shortAddr = shortAddr;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getIeee() {
		return ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public String getBatteryPower() {
		return batteryPower;
	}

	public void setBatteryPower(String batteryPower) {
		this.batteryPower = batteryPower;
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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getSelectKey() {
		return selectKey;
	}

	public void setSelectKey(String selectKey) {
		this.selectKey = selectKey;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<TblDeviceTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<TblDeviceTask> tasks) {
		this.tasks = tasks;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblDevice{" +
			"id=" + id +
			", gatewayId=" + gatewayId +
			", name=" + name +
			", shortAddr=" + shortAddr +
			", endPoint=" + endPoint +
			", profileId=" + profileId +
			", deviceId=" + deviceId +
			", switchStatus=" + switchStatus +
			", online=" + online +
			", ieee=" + ieee +
			", sn=" + sn +
			", zoneType=" + zoneType +
			", batteryPower=" + batteryPower +
			", updateTime=" + updateTime +
			", addTime=" + addTime +
			", data=" + data +
			"}";
	}
}
