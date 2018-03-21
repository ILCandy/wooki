package com.wooki.system.tbl.user.entity;

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
 * @since 2017-12-31
 */
@TableName("tbl_user")
public class TblUser extends Model<TblUser> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String mobile;
	private String nickname;
	@TableField("avatar_url")
	private String avatarUrl;
	private Integer gender;
	@TableField("remote_addr")
	private String remoteAddr;
	@TableField("x_forward_addr")
	private String xForwardAddr;
	@TableField("add_time")
	private Date addTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblUser{" +
			"id=" + id +
			", username=" + username +
			", password=" + password +
			", email=" + email +
			", mobile=" + mobile +
			", nickname=" + nickname +
			", avatarUrl=" + avatarUrl +
			", gender=" + gender +
			", remoteAddr=" + remoteAddr +
			", xForwardAddr=" + xForwardAddr +
			", addTime=" + addTime +
			"}";
	}
}
