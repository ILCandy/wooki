package com.wooki.system.air.gateway.entity;

import javax.persistence.*;

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

    private String u_id;//序列号

    private String allot_company;//分配的公司

    private String gateway_name;//网关名称

    private int status;//状态 1--在线，2 不在线

    private int port;//端口 重新连上的时候端口要更新

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getAllot_company() {
        return allot_company;
    }

    public void setAllot_company(String allot_company) {
        this.allot_company = allot_company;
    }

    public String getGateway_name() {
        return gateway_name;
    }

    public void setGateway_name(String gateway_name) {
        this.gateway_name = gateway_name;
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

    @Override
    public String toString() {
        return "AirGateway{" +
                "id=" + id +
                ", u_id='" + u_id + '\'' +
                ", allot_company='" + allot_company + '\'' +
                ", gateway_name='" + gateway_name + '\'' +
                ", status=" + status +
                ", port=" + port +
                '}';
    }
}
