package com.wooki.system.amme.info.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wooki.system.amme.device.entity.AmmeterDevice;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-02-02
 */
@TableName("ammeter_current_month")
public class AmmeterCurrentMonth extends Model<AmmeterCurrentMonth> {

    private static final long serialVersionUID = 1L;

	public AmmeterCurrentMonth(){}
	public AmmeterCurrentMonth(AmmeterDevice ammeterDevice){
		this.Uuid = ammeterDevice.getUuid();
		// 新加入的电表，总电量就是expand中的总电量
		this.allpower = ammeterDevice.getExpand().getAllpower();
		if(ammeterDevice.getDeviceType()!=null)
			this.type = ammeterDevice.getDeviceType();
	}

	public AmmeterCurrentMonth(AmmeterHourInfo ammeterHourInfo){
		this.Uuid = ammeterHourInfo.getUuid();
		this.allpower = ammeterHourInfo.getAllpower();
	}

    /**
     * 电表Uuid
     */
	private String Uuid;
    /**
     * 当前月的用电总量
     */
	private Double allpower;
	@TableField("add_time")
	private Date addTime;
	@TableField("update_time")
	private Date updateTime;
	private Integer type;


	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String Uuid) {
		this.Uuid = Uuid;
	}

	public Double getAllpower() {
		return allpower;
	}

	public void setAllpower(Double allpower) {
		this.allpower = allpower;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.Uuid;
	}

	@Override
	public String toString() {
		return "AmmeterCurrentMonth{" +
			"Uuid=" + Uuid +
			", allpower=" + allpower +
			", addTime=" + addTime +
			", updateTime=" + updateTime +
			"}";
	}
}
