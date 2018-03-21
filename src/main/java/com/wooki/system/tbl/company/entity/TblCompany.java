package com.wooki.system.tbl.company.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.wooki.system.tbl.company.dto.AddCompanyDto;
import com.wooki.util.common.md5.MD5Util;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@TableName("tbl_company")
public class TblCompany extends Model<TblCompany> {

    private static final long serialVersionUID = 1L;

	public TblCompany(){}
	public TblCompany(AddCompanyDto addCompanyDto){
		this.mobile = addCompanyDto.getMobile();
		this.name = addCompanyDto.getName();
		this.address = addCompanyDto.getAddress();
		this.email = addCompanyDto.getEmail();
		this.logoUrl = addCompanyDto.getLogoUrl();
		this.remoteAddr = addCompanyDto.getRemoteAddr();
		this.password = MD5Util.encode(addCompanyDto.getPassword());
		this.xForwardAddr = addCompanyDto.getXForwardAddr();
		this.type = addCompanyDto.getType();
	}
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String email;
	private String mobile;
	private String password;
	private String name;
	@TableField("logo_url")
	private String logoUrl;
	private String address;
	@TableField("remote_addr")
	private String remoteAddr;
	@TableField("x_forward_addr")
	private String xForwardAddr;
	@TableField("is_verified")
	private Integer isVerified;
	@TableField("add_time")
	private Date addTime;
	private Integer type;
	@TableField("super_id")
	private Integer superId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblCompany{" +
			"id=" + id +
			", email=" + email +
			", mobile=" + mobile +
			", password=" + password +
			", name=" + name +
			", logoUrl=" + logoUrl +
			", address=" + address +
			", remoteAddr=" + remoteAddr +
			", xForwardAddr=" + xForwardAddr +
			", isVerified=" + isVerified +
			", addTime=" + addTime +
			"}";
	}
}
