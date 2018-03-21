package com.wooki.system.tbl.token.entity;

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
@TableName("tbl_token")
public class TblToken extends Model<TblToken> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("token_id")
	private String tokenId;
	@TableField("user_id")
	private Integer userId;
	@TableField("device_id")
	private String deviceId;
	@TableField("device_name")
	private String deviceName;
	private String os;
	@TableField("app_instance_id")
	private String appInstanceId;
	@TableField("app_version")
	private String appVersion;
	@TableField("remote_addr")
	private String remoteAddr;
	@TableField("x_forward_addr")
	private String xForwardAddr;
	@TableField("login_time")
	private Date loginTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAppInstanceId() {
		return appInstanceId;
	}

	public void setAppInstanceId(String appInstanceId) {
		this.appInstanceId = appInstanceId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getxForwardAddr() {
		return xForwardAddr;
	}

	public void setxForwardAddr(String xForwardAddr) {
		this.xForwardAddr = xForwardAddr;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblToken{" +
			"id=" + id +
			", tokenId=" + tokenId +
			", userId=" + userId +
			", deviceId=" + deviceId +
			", deviceName=" + deviceName +
			", os=" + os +
			", appInstanceId=" + appInstanceId +
			", appVersion=" + appVersion +
			", remoteAddr=" + remoteAddr +
			", xForwardAddr=" + xForwardAddr +
			", loginTime=" + loginTime +
			"}";
	}
}
