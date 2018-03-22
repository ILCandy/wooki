package com.wooki.system.air.gateway.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.system.air.gateway.entity
 * @date 2018/3/22
 */
@Entity
@Table(name = "air_device")
public class AirDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; //id主键，自增
    private String deviceName; //设备名称
    private Integer companyId; //所在公司id
    private String gatewayUid; //所在网关的Uid
    private String switchStatus; //空调状态，00停机，01开机，02关机
    private String outAddress; //外地址
    private String inAddress; //内地址
    private Integer groupId; //分区id
    private Integer onlineStatus; //在线状态
    private Date addTime;
    private Date updateTime;
    private String checksum; //查询所有收到的校验码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getGatewayUid() {
        return gatewayUid;
    }

    public void setGatewayUid(String gatewayUid) {
        this.gatewayUid = gatewayUid;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getOutAddress() {
        return outAddress;
    }

    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "AirDevice{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", companyId=" + companyId +
                ", gatewayUid='" + gatewayUid + '\'' +
                ", switchStatus='" + switchStatus + '\'' +
                ", outAddress='" + outAddress + '\'' +
                ", inAddress='" + inAddress + '\'' +
                ", groupId=" + groupId +
                ", onlineStatus=" + onlineStatus +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
