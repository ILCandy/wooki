package com.wooki.system.amme.ammeter.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-21
 */
@TableName("ammeter_login")
public class AmmeterLogin extends Model<AmmeterLogin> {

    private static final long serialVersionUID = 1L;

	private String Uuid;
	private String Aid;
	private String Userid;
	private String Nick;
	private String Pass;
	private String Tel;
	private String phone;
	private String Addr;
	private String Email;
	private String Birthday;
	private String Sign;
	private String Company;
	private String Office;
	private String Imtoken;
	private String Openid;
	private String Avatarpath;
	private Integer Isman;
	private Integer Admin;
	private Integer Status;
	private String Expand;
	private String Adduid;
	private String Lastuid;
	private String Addtime;
	private String Lasttime;
	private String token;

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String Uuid) {
		this.Uuid = Uuid;
	}

	public String getAid() {
		return Aid;
	}

	public void setAid(String Aid) {
		this.Aid = Aid;
	}

	public String getUserid() {
		return Userid;
	}

	public void setUserid(String Userid) {
		this.Userid = Userid;
	}

	public String getNick() {
		return Nick;
	}

	public void setNick(String Nick) {
		this.Nick = Nick;
	}

	public String getPass() {
		return Pass;
	}

	public void setPass(String Pass) {
		this.Pass = Pass;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String Tel) {
		this.Tel = Tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return Addr;
	}

	public void setAddr(String Addr) {
		this.Addr = Addr;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String Birthday) {
		this.Birthday = Birthday;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String Sign) {
		this.Sign = Sign;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String Company) {
		this.Company = Company;
	}

	public String getOffice() {
		return Office;
	}

	public void setOffice(String Office) {
		this.Office = Office;
	}

	public String getImtoken() {
		return Imtoken;
	}

	public void setImtoken(String Imtoken) {
		this.Imtoken = Imtoken;
	}

	public String getOpenid() {
		return Openid;
	}

	public void setOpenid(String Openid) {
		this.Openid = Openid;
	}

	public String getAvatarpath() {
		return Avatarpath;
	}

	public void setAvatarpath(String Avatarpath) {
		this.Avatarpath = Avatarpath;
	}

	public Integer getIsman() {
		return Isman;
	}

	public void setIsman(Integer Isman) {
		this.Isman = Isman;
	}

	public Integer getAdmin() {
		return Admin;
	}

	public void setAdmin(Integer Admin) {
		this.Admin = Admin;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer Status) {
		this.Status = Status;
	}

	public String getExpand() {
		return Expand;
	}

	public void setExpand(String Expand) {
		this.Expand = Expand;
	}

	public String getAdduid() {
		return Adduid;
	}

	public void setAdduid(String Adduid) {
		this.Adduid = Adduid;
	}

	public String getLastuid() {
		return Lastuid;
	}

	public void setLastuid(String Lastuid) {
		this.Lastuid = Lastuid;
	}

	public String getAddtime() {
		return Addtime;
	}

	public void setAddtime(String Addtime) {
		this.Addtime = Addtime;
	}

	public String getLasttime() {
		return Lasttime;
	}

	public void setLasttime(String Lasttime) {
		this.Lasttime = Lasttime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	protected Serializable pkVal() {
		return this.Uuid;
	}

	@Override
	public String toString() {
		return "AmmeterLogin{" +
			"Uuid=" + Uuid +
			", Aid=" + Aid +
			", Userid=" + Userid +
			", Nick=" + Nick +
			", Pass=" + Pass +
			", Tel=" + Tel +
			", phone=" + phone +
			", Addr=" + Addr +
			", Email=" + Email +
			", Birthday=" + Birthday +
			", Sign=" + Sign +
			", Company=" + Company +
			", Office=" + Office +
			", Imtoken=" + Imtoken +
			", Openid=" + Openid +
			", Avatarpath=" + Avatarpath +
			", Isman=" + Isman +
			", Admin=" + Admin +
			", Status=" + Status +
			", Expand=" + Expand +
			", Adduid=" + Adduid +
			", Lastuid=" + Lastuid +
			", Addtime=" + Addtime +
			", Lasttime=" + Lasttime +
			", token=" + token +
			"}";
	}
}
