package com.wooki.system.tbl.metting.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-03-01
 */
@TableName("tbl_metting_reserve")
public class TblMettingReserve extends Model<TblMettingReserve> {

    private static final long serialVersionUID = 1L;

    /**
     * 预定会议室信息的id
     */
	private Long id;
    /**
     * 临时密码
     */
	@TableField("temp_pwd")
	private String tempPwd;
    /**
     * 公司id
     */
	@TableField("company_id")
	private Integer companyId;
	@TableField("company_name")
	private String companyName;
	@TableField("begin_time")
	private String beginTime;
	@TableField("end_time")
	private String endTime;
    /**
     * 状态 0、失效 1、有效
     */
	private Integer status;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	@TableField("super_id")
	private Integer superId;
	@TableField("device_id")
	private Long deviceId;
	private String qrcode;
	@TableField("pic_url")
	private String picUrl;
	@TableField("metting_id")
	private String mettingId;
	@TableField("metting_name")
	private String mettingName;


	/**
	 * 密码为二维码的时候，返回的数字密码
	 * @return
	 */
	@TableField("qrcode_content")
	private String qrcodeContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTempPwd() {
		return tempPwd;
	}

	public void setTempPwd(String tempPwd) {
		this.tempPwd = tempPwd;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getQrcodeContent() {
		return qrcodeContent;
	}

	public void setQrcodeContent(String qrcodeContent) {
		this.qrcodeContent = qrcodeContent;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setMettingId(String mettingId) {
		this.mettingId = mettingId;
	}

	public String getMettingId() {
		return mettingId;
	}

	public void setMettingName(String mettingName) {
		this.mettingName = mettingName;
	}

	public String getMettingName() {
		return mettingName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblMettingReserve{" +
			"id=" + id +
			", tempPwd=" + tempPwd +
			", companyId=" + companyId +
			", companyName=" + companyName +
			", beginTime=" + beginTime +
			", endTime=" + endTime +
			", status=" + status +
			", createTime=" + createTime +
			", picUrl=" + picUrl +
			", mettingName=" + mettingName +
			", mettingId=" + mettingId +
			"}";
	}
}
