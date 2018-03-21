package com.wooki.system.tbl.bindGateway.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.wooki.system.tbl.bindGateway.dto.BindGayewayDto;

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
@TableName("tbl_bind_gateway")
public class TblBindGateway extends Model<TblBindGateway> {

    private static final long serialVersionUID = 1L;

	public TblBindGateway(){}
	public TblBindGateway(BindGayewayDto bindGayewayDto){
		this.companyId = bindGayewayDto.getCompanyId();
		this.gatewayId = bindGayewayDto.getGatewayId();
	}
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("gateway_id")
	private Integer gatewayId;
	@TableField("company_id")
	private Integer companyId;
	@TableField("add_time")
	private Date addTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
		return "TblBindGateway{" +
			"id=" + id +
			", gatewayId=" + gatewayId +
			", companyId=" + companyId +
			", addTime=" + addTime +
			"}";
	}
}
