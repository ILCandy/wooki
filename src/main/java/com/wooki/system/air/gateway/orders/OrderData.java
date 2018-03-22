package com.wooki.system.air.gateway.orders;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.system.air.gateway.orders
 * @date 2018/3/22
 */
public class OrderData {

    //查询该网关下所有列表
    public static final byte[] QUERY_ALL = {0x01,0x50,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,0x4D};

    //关闭该网关下所有列表
    public static final byte[] CLOSE_ALL = {0x01,0x31,0X02,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,0x30};

    //打开该网关下所有列表
    public static final byte[] OPEN_ALL = {0x01,0x31,0X01,(byte) 0xFF,(byte) 0xFF,(byte) 0xFF,0x31};

}
