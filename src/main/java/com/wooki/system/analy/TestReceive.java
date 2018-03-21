package com.wooki.system.analy;

import lombok.Data;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 17/12/31
 * Time : 下午5:08
 */
@Data
public class TestReceive {
    private String act;
    private Integer fd;
    private Integer gatewayId;
    private String data;
}
