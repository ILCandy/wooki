package com.wooki.system.tbl.company.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-13
 */
@TableName("tbl_company_work")
public class TblCompanyWork extends Model<TblCompanyWork> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 公司id
     */
	@TableField("company_id")
	private Integer companyId;
	/**
	 * 更新时间
	 */
	@TableField("add_time")
	private Date addTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 工作时间 包含上午和下午
     */
	@TableField("work_time")
	private String workTime;
    /**
     * 午休时间
     */
	@TableField("midday_rest")
	private String middayRest;
    /**
     * 下班加班时间
     */
	@TableField("come_off_work")
	private String comeOffWork;
    /**
     * 电费
     */
	@TableField("electric_charge")
	private Double electricCharge;
    /**
     * 上班周期 ，是否大小周等 1 为都一样， 2为 大小周
     */
	private Integer period;
    /**
     * 如果是大小周，一家公司会有两条记录，这个字段判别这条信息是否正在被用
     */
	@TableField("use_flag")
	private Integer useFlag;
    /**
     * 上班天数  1,2,3,4,5 或 1,2,3,4,5,6
     */
	@TableField("work_days")
	private String workDays;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getMiddayRest() {
		return middayRest;
	}

	public void setMiddayRest(String middayRest) {
		this.middayRest = middayRest;
	}

	public void setComeOffWork(String comeOffWork) {
		this.comeOffWork = comeOffWork;
	}

	public String getComeOffWork() {
		return comeOffWork;
	}

	public Double getElectricCharge() {
		return electricCharge;
	}

	public void setElectricCharge(Double electricCharge) {
		this.electricCharge = electricCharge;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblCompanyWork{" +
			"id=" + id +
			", companyId=" + companyId +
			", updateTime=" + updateTime +
			", workTime=" + workTime +
			", middayRest=" + middayRest +
			", comeOffWork=" + comeOffWork +
			", electricCharge=" + electricCharge +
			", period=" + period +
			", useFlag=" + useFlag +
			", workDays=" + workDays +
			"}";
	}
}
