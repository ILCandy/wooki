package com.wooki.system.tbl.lock.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-02-28
 */
public class TblLockDeviceDto extends Model<TblLockDeviceDto> {

    private static final long serialVersionUID = 1L;

	public TblLockDeviceDto(){}
	public TblLockDeviceDto(LockSetCompanyDto lockSetCompanyDto){
		this.id = lockSetCompanyDto.getLockId();
		this.companyId = lockSetCompanyDto.getCompanyId();
	}
    /**
     * 设备id
     */
	private Long id;
    /**
     * 设备名
     */
	private String dev_name;
    /**
     * 门口主机设备序列号
     */
	private String dev_sn;
    /**
     * 设备类型
     */
	private Integer dev_type;
    /**
     * 设备门编号
     */
	private String door_no;
    /**
     * 楼层数量
     */
	private Integer floor_count;
    /**
     * 读写卡扇区地址 ( 1-15 )
     */
	private Integer section;
    /**
     * 扇区密码（同时也为生成临时密码的秘钥）
     */
	private String section_key;
	@TableField("company_id")
	private Integer companyId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public String getDev_sn() {
		return dev_sn;
	}

	public Integer getDev_type() {
		return dev_type;
	}

	public String getDev_name() {
		return dev_name;
	}

	public void setDev_name(String dev_name) {
		this.dev_name = dev_name;
	}

	public void setDev_sn(String dev_sn) {
		this.dev_sn = dev_sn;
	}

	public void setDev_type(Integer dev_type) {
		this.dev_type = dev_type;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getFloor_count() {
		return floor_count;
	}

	public void setFloor_count(Integer floor_count) {
		this.floor_count = floor_count;
	}

	public String getDoor_no() {
		return door_no;
	}

	public void setDoor_no(String door_no) {
		this.door_no = door_no;
	}

	public String getSection_key() {
		return section_key;
	}

	public void setSection_key(String section_key) {
		this.section_key = section_key;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TblLockDevice{" +
			"id=" + id +
//			", devName=" + dev_name +
//			", devSn=" + dev_sn +
//			", devType=" + dev_type +
//			", doorNo=" + door_no +
//			", floorCount=" + floor_count +
			", section=" + section +
//			", sectionKey=" + section_key +
			"}";
	}
}
