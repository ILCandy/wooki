package com.wooki.system.amme.ammeter.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.wooki.system.amme.ammeter.dto.IdAmmeterDto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-26
 */
public class Ammeter extends Model<Ammeter> {

    private static final long serialVersionUID = 1L;

	public Ammeter(){}
	public Ammeter(IdAmmeterDto idAmmeterDto){
		this.id = idAmmeterDto.getId();
		this.companyId = idAmmeterDto.getCompanyId();
	}
    /**
     * 用户id
     */
	private String id;
	private Double allpower;
	private Date addtime;
	@TableField("update_time")
	private Date updateTime;
    /**
     * 所属公司
     */
	@TableField("company_id")
	private Integer companyId;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAllpower() {
		return allpower;
	}

	public void setAllpower(Double allpower) {
		this.allpower = allpower;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Ammeter{" +
			"id=" + id +
			", allpower=" + allpower +
			", addtime=" + addtime +
			", updateTime=" + updateTime +
			", companyId=" + companyId +
			"}";
	}
}
