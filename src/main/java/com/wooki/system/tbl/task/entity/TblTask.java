package com.wooki.system.tbl.task.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.task.dto.TaskAddDto;
import com.wooki.system.tbl.task.dto.TaskDelDto;
import com.wooki.system.tbl.task.dto.TaskUpdateStatusDto;
import com.wooki.util.common.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
@TableName("tbl_task")
public class TblTask extends Model<TblTask> {

    private static final long serialVersionUID = 1L;

	public TblTask(){}
	public TblTask(TaskAddDto taskAddDto){
		this.sceneId = taskAddDto.getSceneId();
		this.name = taskAddDto.getName();
		this.enable = taskAddDto.getEnable();
		this.frequency = taskAddDto.getFrequency();
		// 转换成每一天的时间戳
		this.time = DateUtil.taskTime(taskAddDto.getDateStr());
		this.timeStr = taskAddDto.getDateStr();
		this.addTime = new Date();
		if(taskAddDto.getFrequency().equals(Constant.FREQUENCY_TRUE)&& !StringUtils.isEmpty(taskAddDto.getDays())) {
			// 每一天
			this.days = DateUtil.getDaysToNumStr(taskAddDto.getDays());
		}
	}
	public TblTask(TaskDelDto taskDelDto){
		this.id = taskDelDto.getTaskId();
	}

	public TblTask(TaskUpdateStatusDto taskUpdateStatusDto){
		this.id = taskUpdateStatusDto.getId();
		this.enable = Integer.valueOf(Constant.map.get(taskUpdateStatusDto.getStatus()));
	}

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String name;
	@TableField("update_time")
	private Date updateTime;
	@TableField("add_time")
	private Date addTime;
    /**
     * 0 关 1开
     */
	private Integer enable;
    /**
     * 是否频率 0 单次 1 频率
     */
	private Integer frequency;
    /**
     * 当天的执行时间
     */
	private Long time;
    /**
     * 情景id
     */
	@TableField("scene_id")
	private Integer sceneId;
	/**
	 * 日期
	 */
	private String days;
	@TableField("time_str")
	private String timeStr;

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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblTask{" +
			"id=" + id +
			", name=" + name +
			", updateTime=" + updateTime +
			", addTime=" + addTime +
			", enable=" + enable +
			", frequency=" + frequency +
			", time=" + time +
			", sceneId=" + sceneId +
			", timeStr=" + timeStr +
			"}";
	}
}
