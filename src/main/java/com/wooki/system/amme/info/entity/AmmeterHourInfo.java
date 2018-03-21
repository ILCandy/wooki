package com.wooki.system.amme.info.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2018-01-27
 */
@TableName("ammeter_hour_info")
public class AmmeterHourInfo extends Model<AmmeterHourInfo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String Uuid;
	private Date time;
    /**
     * 跟上次纪录的电量差值
     */
	private Double allpower;
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String Uuid) {
		this.Uuid = Uuid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getAllpower() {
		return allpower;
	}

	public void setAllpower(Double allpower) {
		this.allpower = allpower;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AmmeterHourInfo{" +
			"id=" + id +
			", Uuid=" + Uuid +
			", time=" + time +
			", allpower=" + allpower +
			"}";
	}
}
