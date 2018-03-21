package com.wooki.system.tbl.link.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-24
 */
@TableName("tbl_link")
public class TblLink extends Model<TblLink> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 触发设备的短地址
     */
	@TableField("short_addr")
	private String shortAddr;
	@TableField("company_id")
	private Integer companyId;
    /**
     * 触发时间 ,  9:00~12:00 ,1:00 ~ 3:00 ，逗号分隔开
     */
	@TableField("link_time")
	private String linkTime;
    /**
     * 设备ids列表->1,2,3,4
     */
	@TableField("device_status")
	private String deviceStatus;
    /**
     * 添加时间
     */
	private Date addtime;
	@TableField("update_time")
	private Date updateTime;
	@TableField("end_point")
	private String endPoint;
	private String status;
	private String days;
	private String time;
	@TableField("select_key")
	private String selectKey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortAddr() {
		return shortAddr;
	}

	public void setShortAddr(String shortAddr) {
		this.shortAddr = shortAddr;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getLinkTime() {
		return linkTime;
	}

	public void setLinkTime(String linkTime) {
		this.linkTime = linkTime;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSelectKey() {
		return selectKey;
	}

	public void setSelectKey(String selectKey) {
		this.selectKey = selectKey;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblLink{" +
			"id=" + id +
			", shortAddr=" + shortAddr +
			", linkTime=" + linkTime +
			", deviceStatus=" + deviceStatus +
			", addtime=" + addtime +
			", updateTime=" + updateTime +
			", days=" + days +
			", selectKey=" + selectKey +
			"}";
	}
}
