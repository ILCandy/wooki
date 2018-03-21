package com.wooki.system.tbl.report.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.wooki.system.tbl.report.dto.ReportDto;

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
@TableName("tbl_report")
public class TblReport extends Model<TblReport> {

    private static final long serialVersionUID = 1L;


	public TblReport(){}
	public TblReport(ReportDto reportDto){
		this.addTime = new Date();
		this.fd = reportDto.getFd();
		this.data = reportDto.getData();
	}
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("gateway_id")
	private Integer gatewayId;
	private Integer fd;
	private Integer sn;
	private String data;
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

	public Integer getFd() {
		return fd;
	}

	public void setFd(Integer fd) {
		this.fd = fd;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
		return "TblReport{" +
			"id=" + id +
			", gatewayId=" + gatewayId +
			", fd=" + fd +
			", sn=" + sn +
			", data=" + data +
			", addTime=" + addTime +
			"}";
	}
}
