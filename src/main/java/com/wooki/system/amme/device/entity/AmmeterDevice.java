package com.wooki.system.amme.device.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wooki.system.amme.ammeter.dto.IdAmmeterDto;
import com.wooki.system.amme.device.dto.DeviceSetTypeDto;
import com.wooki.system.amme.expand.entity.DeviceExpand;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-26
 */
@TableName("ammeter_device")
public class AmmeterDevice extends Model<AmmeterDevice> {

    private static final long serialVersionUID = 1L;

	public AmmeterDevice(){}
	public AmmeterDevice(IdAmmeterDto idAmmeterDto){
		this.Uuid = idAmmeterDto.getId();
		this.companyId = idAmmeterDto.getCompanyId();
	}
	public AmmeterDevice(DeviceSetTypeDto deviceSetTypeDto){
		this.Uuid = deviceSetTypeDto.getId();
		this.deviceType = deviceSetTypeDto.getType();
	}

	@TableField("Uuid")
	private String Uuid;
	@TableField("Aid")
	private String Aid;
	@TableField("Cid")
	private String Cid;
	@TableField("Pid")
	private String Pid;
	@TableField("Isnode")
	private Integer Isnode;
	@TableField("Jid")
	private Integer Jid;
	@TableField("Type")
	private Integer Type;
	@TableField("Devtype")
	private Integer Devtype;
	@TableField("Devno")
	private Integer Devno;
	@TableField("Title")
	private String Title;
	@TableField("Picture")
	private String Picture;
	@TableField("Value")
	private Integer Value;
	@TableField("Expandvalue")
	private Integer Expandvalue;
	@TableField("status")
	private Integer status;
	@TableField("Disable")
	private Integer Disable;
	@TableField("Order")
	private Integer Order;
	@TableField("Price")
	private Double Price;
	@TableField("Money")
	private Double Money;
	@TableField("Token")
	private String Token;
	@TableField("Authorizes")
	private String Authorizes;
	@TableField("company_id")
	private Integer companyId;
    /**
     * 存放json字符串   参数内容[电表有mode=0后付费、1预付费
apportion公摊、threshold、ammetercollection电表采集时间]
     */
	private String Param;
	@TableField("Expand_id")
	private Integer ExpandId;
	@TableField("device_type")
	private Integer deviceType;
	@TableField("External_id")
	private Integer ExternalId;
	private String Adduid;
	private String Lastuid;
	private String Addtime;
	private String Lasttime;

	@TableField(exist = false)
	private DeviceExpand Expand;


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

	public String getCid() {
		return Cid;
	}

	public void setCid(String Cid) {
		this.Cid = Cid;
	}

	public String getPid() {
		return Pid;
	}

	public void setPid(String Pid) {
		this.Pid = Pid;
	}

	public Integer getIsnode() {
		return Isnode;
	}

	public void setIsnode(Integer Isnode) {
		this.Isnode = Isnode;
	}

	public Integer getJid() {
		return Jid;
	}

	public void setJid(Integer Jid) {
		this.Jid = Jid;
	}

	public Integer getType() {
		return Type;
	}

	public void setType(Integer Type) {
		this.Type = Type;
	}

	public Integer getDevtype() {
		return Devtype;
	}

	public void setDevtype(Integer Devtype) {
		this.Devtype = Devtype;
	}

	public Integer getDevno() {
		return Devno;
	}

	public void setDevno(Integer Devno) {
		this.Devno = Devno;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getPicture() {
		return Picture;
	}

	public void setPicture(String Picture) {
		this.Picture = Picture;
	}

	public Integer getValue() {
		return Value;
	}

	public void setValue(Integer Value) {
		this.Value = Value;
	}

	public Integer getExpandvalue() {
		return Expandvalue;
	}

	public void setExpandvalue(Integer Expandvalue) {
		this.Expandvalue = Expandvalue;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDisable() {
		return Disable;
	}

	public void setDisable(Integer Disable) {
		this.Disable = Disable;
	}

	public Integer getOrder() {
		return Order;
	}

	public void setOrder(Integer Order) {
		this.Order = Order;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double Price) {
		this.Price = Price;
	}

	public Double getMoney() {
		return Money;
	}

	public void setMoney(Double Money) {
		this.Money = Money;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String Token) {
		this.Token = Token;
	}

	public String getAuthorizes() {
		return Authorizes;
	}

	public void setAuthorizes(String Authorizes) {
		this.Authorizes = Authorizes;
	}

	public String getParam() {
		return Param;
	}

	public void setParam(String Param) {
		this.Param = Param;
	}

	public Integer getExpandId() {
		return ExpandId;
	}

	public void setExpandId(Integer ExpandId) {
		this.ExpandId = ExpandId;
	}

	public Integer getExternalId() {
		return ExternalId;
	}

	public void setExternalId(Integer ExternalId) {
		this.ExternalId = ExternalId;
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

	public DeviceExpand getExpand() {
		return Expand;
	}

	public void setExpand(DeviceExpand expand) {
		Expand = expand;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	protected Serializable pkVal() {
		return this.Uuid;
	}

	@Override
	public String toString() {
		return "AmmeterDevice{" +
			"Uuid=" + Uuid +
			", Aid=" + Aid +
			", Cid=" + Cid +
			", Pid=" + Pid +
			", Isnode=" + Isnode +
			", Jid=" + Jid +
			", Type=" + Type +
			", Devtype=" + Devtype +
			", Devno=" + Devno +
			", Title=" + Title +
			", Picture=" + Picture +
			", Value=" + Value +
			", Expandvalue=" + Expandvalue +
			", status=" + status +
			", Disable=" + Disable +
			", Order=" + Order +
			", Price=" + Price +
			", Money=" + Money +
			", Token=" + Token +
			", Authorizes=" + Authorizes +
			", Param=" + Param +
			", ExpandId=" + ExpandId +
			", ExternalId=" + ExternalId +
			", Adduid=" + Adduid +
			", Lastuid=" + Lastuid +
			", Addtime=" + Addtime +
			", Lasttime=" + Lasttime +
			"}";
	}
}
