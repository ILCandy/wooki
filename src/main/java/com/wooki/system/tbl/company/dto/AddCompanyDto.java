package com.wooki.system.tbl.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 上午10:39
 */
@Data
public class AddCompanyDto {
    private String email;
    @NotNull(message = "电话不能为空")
    private String mobile;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "公司名不能为空")
    private String name;
    private String logoUrl;
    @NotNull(message = "公司地址不能为空")
    private String address;
    @NotNull(message = "用户类型不能为空")
    private Integer type;
    private String remoteAddr;
    private String xForwardAddr;
}
