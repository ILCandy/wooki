package com.wooki.system.air.gateway.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.air.gateway.entity
 * @date 2018/3/19
 */
@Entity
@Table(name = "air_gateway")
public class AirGateway {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String uid;//序列号

    private String allotCompany;//分配的公司

    private String gatewayName;//网关名称

    private int status;//状态 1--在线，2 不在线

    private int port;//端口 重新连上的时候端口要更新

    private int companyid;

    private Date addTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAllotCompany() {
        return allotCompany;
    }

    public void setAllotCompany(String allotCompany) {
        this.allotCompany = allotCompany;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    @Override
    public String toString() {
        return "AirGateway{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", allotCompany='" + allotCompany + '\'' +
                ", gatewayName='" + gatewayName + '\'' +
                ", status=" + status +
                ", port=" + port +
                ", companyid=" + companyid +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
