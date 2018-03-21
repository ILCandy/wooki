package com.wooki.system.tbl.metting.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.metting.dto.MettingAddDto;
import com.wooki.system.tbl.metting.dto.MettingSetBUserDto;
import com.wooki.system.tbl.metting.dto.MettingSetSwitchDto;
import com.wooki.system.tbl.metting.dto.MettingUpdateDto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-02-23
 */
@TableName("tbl_metting")
public class TblMetting extends Model<TblMetting> {

    private static final long serialVersionUID = 1L;
	public TblMetting(){}
	public TblMetting(TblCompany company, MettingAddDto mettingAddDto){
//		this.id = UUID.randomUUID().toString().replaceAll("-","");
		this.name = mettingAddDto.getName();
		this.addTime = new Date();
//		this.companyId = company.getId();
	}
	public TblMetting(MettingUpdateDto mettingUpdateDto){
//		this.id = Long.valueOf();
//		this.name = mettingUpdateDto.getName();
		init(mettingUpdateDto.getId(),null,mettingUpdateDto.getName());
	}
	public TblMetting(MettingSetSwitchDto mettingSetSwitchDto){
		this.id = Long.valueOf(mettingSetSwitchDto.getId());
		this.switchId = Long.valueOf(mettingSetSwitchDto.getSwitchId());
	}
	public TblMetting(MettingSetBUserDto mettingSetBUserDto){
		init(mettingSetBUserDto.getMettingId(),mettingSetBUserDto.getCompanyId(),null);
	}

	private void init(Long mettingId,Integer companyId,String name){
		this.id = mettingId;
		this.companyId = companyId;
		this.name = name;
	}



	private Long id;
	private String name;
	@TableField("add_time")
	private Date addTime;
	@TableField("company_id")
	private Integer companyId;
	@TableField("switch_id")
	private Long switchId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getSwitchId() {
		return switchId;
	}

	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblMetting{" +
			"id=" + id +
			", name=" + name +
			", addTime=" + addTime +
			", companyId=" + companyId +
			"}";
	}
}
