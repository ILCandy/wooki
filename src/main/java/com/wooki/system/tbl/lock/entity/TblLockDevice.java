package com.wooki.system.tbl.lock.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wooki.system.tbl.lock.dto.LockSetCompanyDto;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author whn
 * @since 2018-02-28
 */
@TableName("tbl_lock_device")
public class TblLockDevice extends Model<TblLockDevice> {

    private static final long serialVersionUID = 1L;

	public TblLockDevice(){}
	public TblLockDevice(LockSetCompanyDto lockSetCompanyDto){
		this.id = lockSetCompanyDto.getLockId();
		this.companyId = lockSetCompanyDto.getCompanyId();
	}
    /**
     * 设备id
     */
	@TableId(value="id")
	private Long id;
    /**
     * 设备名
     */
	@TableField("dev_name")
	private String devName;
    /**
     * 门口主机设备序列号
     */
	@TableField("dev_sn")
	private String devSn;
    /**
     * 设备类型
     */
	@TableField("dev_type")
	private Integer devType;
    /**
     * 设备门编号
     */
	@TableField("door_no")
	private String doorNo;
    /**
     * 楼层数量
     */
	@TableField("floor_count")
	private Integer floorCount;
    /**
     * 读写卡扇区地址 ( 1-15 )
     */
	private Integer section;
    /**
     * 扇区密码（同时也为生成临时密码的秘钥）
     */
	@TableField("section_key")
	private String sectionKey;
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

	public void setDevType(Integer devType) {
		this.devType = devType;
	}

	public Integer getDevType() {
		return devType;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getDevName() {
		return devName;
	}

	public String getDevSn() {
		return devSn;
	}

	public void setDevSn(String devSn) {
		this.devSn = devSn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}

	public void setSectionKey(String sectionKey) {
		this.sectionKey = sectionKey;
	}

	public String getSectionKey() {
		return sectionKey;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	public String getDoorNo() {
		return doorNo;
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
