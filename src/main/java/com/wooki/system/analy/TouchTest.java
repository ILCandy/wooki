package com.wooki.system.analy;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 17/12/31
 * Time : 上午11:28
 */
public class TouchTest {

    private Byte[] length ;   // 总长度 2 字节,包含自己
    private Byte[] sn;        // SN号  4 字节
    private Byte[] aa;        // 6 字节 未确定
    private Byte ctlSign;     // 控制标志 1字节
    private Byte ctlType;     // 控制类型 1字节   such as 0x82
    private Byte followLength;// 后续长度，不包括自己 1字节
    private Byte pattern;     // 地址模式 1字节
    private Byte lowAddress;  // 短地址低位 1字节
    private Byte highAddress; // 短地址高位 1字节
    private Byte[] reserve;   // 保留位  6字节
    private Byte endPoint;    // 多路终端的终端号 1字节  1~240?
    private Byte[] lastReserve;   // 保留位  2字节
    private Byte control;     // 控制 0x01 比如 082 ，1/0/2 : 开/关/停
}
