package com.wooki.system.amme.expand.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wooki.system.amme.ammeter.dto.IdAmDeviceDto;
import com.wooki.util.common.date.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-25
 */
@TableName("ammeter_device_expand")
public class DeviceExpand extends Model<DeviceExpand> {

    private static final long serialVersionUID = 1L;

	public DeviceExpand(){}
	public DeviceExpand(IdAmDeviceDto idAmDeviceDto){
		initId(idAmDeviceDto.getId());
	}

	private void initId(String Uuid ){
		this.Uuid = Uuid;
	}
	private String Uuid;
	private Double allpower;
	private Double current;
	@TableField(exist = false)
	private Map lasttime;
	private Double surplus;
	private Double voltage;
	@TableField("last_time")
	private Date lastTime;

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

	public Double getCurrent() {
		return current;
	}

	public void setCurrent(Double current) {
		this.current = current;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Map getLasttime() {
		return lasttime;
	}

	public void setLasttime(Map lasttime) throws Exception{
		// 解析信息
		this.lasttime = lasttime;
		// 数据库信息
		this.lastTime =DateUtil.getDateByString(DateUtil.dealDateFormat((String)lasttime.get("time")));
	}

	public Double getSurplus() {
		return surplus;
	}

	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	@Override
	protected Serializable pkVal() {
		return this.Uuid;
	}

	@Override
	public String toString() {
		return "DeviceExpand{" +
			"Uuid=" + Uuid +
			", allpower=" + allpower +
			", current=" + current +
			", lasttime=" + lasttime +
			", surplus=" + surplus +
			", voltage=" + voltage +
			", lastTime=" + lastTime +
			"}";
	}
}
