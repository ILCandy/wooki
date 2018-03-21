package com.wooki.system.amme.info.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-31
 */
@TableName("ammeter_day_info")
public class AmmeterDayInfo extends Model<AmmeterDayInfo> {

    private static final long serialVersionUID = 1L;

	public AmmeterDayInfo(){}
	public AmmeterDayInfo(String Uuid,String timeStr){
		this.Uuid = Uuid;
		this.time = timeStr;
	}
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 电表Uuid
     */
	private String Uuid;
	private String time;
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

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
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
		return "AmmeterDayInfo{" +
			"id=" + id +
			", Uuid=" + Uuid +
			", time=" + time +
			", allpower=" + allpower +
			"}";
	}
}
