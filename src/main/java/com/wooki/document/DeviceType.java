package com.wooki.document;

/**
 * 设备类型
 * <p/>
 * Created by infelt on 2016/3/3.
 */
public final class DeviceType {

   //devi
    public static final int ID_NOTEPAPER = 0x0000;                          // 普通开关(随意贴)
    public static final int ID_SWITCH = 0x0002;                             // 开关
    public static final int ID_SWITCH_SCENE = 0x0004;                      // 场景开关
    public static final int ID_SOCKET = 0x0009;                             // 插座
    public static final int ID_SMARTSOCKET = 0x0051;                         //智能插座
    public static final int ID_SMARTMETER = 0x0053;                         //智能测量设备
    public static final int ID_NATURE_LIGHT_DIM1 = 0x0100;                // PROFILEID_ZHA 开关灯/PROFILEID_ZLL 调光灯
    public static final int ID_LIGHT_DIM = 0x0101;                         // 调光灯
    public static final int ID_NATURE_LIGHT_DIM2 = 0x0200;                 // PROFILEID_ZHA 窗帘布/PROFILEID_ZLL 调光灯
    public static final int ID_LIGHT_RGB_OUT_LP = 0x0102;                  // 彩灯无自动变色功能
    public static final int ID_LIGHT_CT = 0x0110;                          // 色温灯
    public static final int ID_LIGHT_CT2 = 0x0220;                          // 色温灯
    public static final int ID_LIGHT_RGB_IN_LP = 0x0210;                 // 彩灯有自动变色功能
    public static final int ID_CURTAIN = 0x0202;                            // 窗帘
    public static final int ID_CONTROL_JZ = 0x0800;                         // 九州遥控器
    public static final int ID_CONTROL_N = 0x0810;                          // 遥控器
    public static final int ID_AUDIBLE_ALARM = 0x0403;                      // 声光报警器
    public static final int ID_IR_CONTROL = 0x0161;                         // 红外转发器
    public static final int ID_SENSOR_LIGHT = 0x0106;                    // 光照
    // public static final int ID_SENSOR_HUMAN		= 0x0107; // 人体
    public static final int ID_SENSOR_THERMOSTAT = 0x0301;               //温控器
    public static final int ID_SENSOR_THTB = 0x0302;                        // 温湿度
    public static final int ID_FAN_CONTROL = 0x0303;                        // 温湿度
    public static final int ID_SENSOR_PM250 = 0x0309;                       // PM 2.5
    public static final int ID_SENSOR_ZLL = 0x0402;                          // 传感器
    public static final int ID_DOOR_LOCK = 0x000A;                           // 门锁
    public static final int PROFILEID_ZHA = 0x0104;
    public static final int PROFILEID_ZLL = 0xc05e;
    public final class ZoneType {
        // public static final int SENSOR_CIE= 0x0000; //CIE
        public static final int SENSOR_DOOR2= 0x00FF;
        public static final int SENSOR_DOOR = 0x0015;                       //门磁传感器
        public static final int SENSOR_HUMAN = 0x000D;                      //人体传感器
        public static final int SENSOR_SMOKE = 0x0028;                       //火焰传感器 (烟雾)
        public static final int SENSOR_SMOKE2 = 0x8000;                     //火焰传感器 (烟雾)
        public static final int SENSOR_GAS = 0x002B;                         //气体传感器（燃气）
        public static final int SENSOR_CO = 0x8001;                          //一氧化碳
        public static final int SENSOR_VIBRATE = 0x002D;                     //震动传感器
        public static final int SENSOR_WATER = 0x002A;                      //水位传感器（水浸）
        public static final int SENSOR_SOS = 0x002C;                        //紧急按钮
        public static final int SENSOR_SECURITY = 0x0115;                   //安防传感器
    }
}
