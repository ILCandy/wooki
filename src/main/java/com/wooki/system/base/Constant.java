package com.wooki.system.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 17/10/25
 * Time : 上午11:44
 */
public class Constant {

    // 管理员手机号, 改用类型判断
    public static final String ADMIN_PHONE = "15766227558";
    // type
    public static final String ADMIN = "admin";
    public static final String BUser = "buser";
    public static final String CUser = "cuser";
//    public static final Integer USER_TYPE_ADMIN = 1000;
//    public static final Integer USER_TYPE_BUSER = 500;
//    public static final Integer USER_TYPE_CUSER = 400;

    // time
    public static final Long ONE_DAY = 60 * 60 *24L;
    public static final Long THREE_HOUR = 60 * 60 * 3L;
    public static final Long SIX_HOUR = 60 * 60 * 6L;

    // 最近12个月的电表信息
    public static final Integer nearMonthLimit = 12;

    public static final Integer IEEE_LENGTH = 8;

    // 默认值
    public static final int INFO_DEFAULT = 0;
    // 是否是频率
    public static final int FREQUENCY_FALSE = 0;
    public static final int FREQUENCY_TRUE = 1;
    // 预约信息是否使用
    public static final int METTING_RESERVE_TRUE = 1;
    public static final int METTING_RESERVE_FALSE = 0;

    public static final String TIMESPLITSTR = "~";
    public static final Long TIME_DIFFERENCE = 1000L * 30; // 联动触发时间差 30秒

    public static final int RECEIVE_TYPE_INDEX = 6; // 第七位，数组下标从0开始
    public static final String LIST_TYPE = "01";    // 暂时是设备列表的上报
    public static final String OPERATE_DEVICE_TYPE = "29"; // 现是收到，29
    public static final String DEVICE_OPERATE_TYPE = "07"; // 控制设备开关结果的上报
    public static final String DEVICE_REPORT_TYPE = "70";  // 设备主动上报的类型

    //目前，是门磁和红外 ，触发联动
    public static final String LIST_LINK_DEVICE_TYPE = "0402,0161,0500";

    public static final Integer HAS_BIG_AND_SMALL = 1; // 1的话就是有大小周
    public static final String SMALL_WEEK_DAYS = "2,3,4,5,6"; // 小周
    public static final String BIG_WEEK_DAYS = "2,3,4,5,6,7"; // 大周
    public static final String SMALL_WEEK = "small";
    public static final String BIG_WEEK = "big";

    public static final Integer tenSec = 10 *1000;
    // 是否开通使用
    public static final int ENABLE_FALSE = 0;
    public static final int ENABLE_TRUE = 1;

    public static final String KEY_DATA = "Data";
    public static final String LOW_KEY_DATA = "data";
    public static final String KEY_EXPAND = "Expand";
    public static final String KEY_TOKEN = "token";
    public static final String HEADER_KEY_TOKEN = "token";
    public static final String KEY_COUNT = "count";

    public static final String TASK_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String METHOD_GET_GATEWAYID = "getGatewayId";
    public static final String METHOD_GET_DATA = "getData";
    public static final String METHOD_SET_DATA = "setData";
    public static final String METHOD_SET_TOKEN = "setToken";

    // 门禁相关
    public static final String LOCK_ACCESS_TOKEN = "access_token";
    public static final String LOCK_OPERATION = "operation";
    public static final String LOCK_TEMP_PWD = "temp_pwd";
    public static final String LOCK_TEMP_PWD_QRCORD = "qrcode";
    public static final String LOCK_TEMP_PWD_QRCORD_NUMBER = "qrcode_content";

    // 会议室预定相关
    public static final Integer RESERVE_FALSE = 0;
    public static final Integer RESERVE_TRUE = 1;

    // 分页相关
    public static final String PAGE_LIMIT = "limit";
    public static final String PAGE_OFFSET = "offset";

    // return info
    public static final String RETURN_MSG = "msg";
    public static final String OK = "ok";
    public static final Integer ONCE_SENDMSG_COUNT = 20;// 定时任务，一次性发多少个设备的信息,

    public static final String OPERATE_SUCCESS = "操作成功";
    public static final String OPERATE_FAIL = "操作失败";

    public static final Integer USERNAME_PASSWORD_ERROR_CODE = 451;
    public static final Integer LACK_TOKEN_ERROR_CODE = 999;
    public static final Integer NO_PERMISSION_CODE = 1000;
    public static final Integer THREAD_START_ERROR_CODE = 1001;
    public static final Integer PASSWORD_ERROR_CODE = 1002;
    public static final Integer LOCK_HAS_BIND_METTING_ERROR_CODE = 1003;
    public static final Integer RESERVE_NOT_EXIST_ERROR_CODE = 1004;
    public static final Integer METTING_HAS_NOT_LOCK_DEVICE_CODE = 1005;
    public static final Integer ONLY_CAN_CANCEL_MYSELY_ERROR_CODE = 1006;
    public static final Integer MOBILE_EXIST_ERROR_CODE = 1007;

    public static final String USERNAME_PASSWORD_ERROR = "账号或者密码错误";
    public static final String LACK_TOKEN_ERROR = "缺少token";
    public static final String NO_PERMISSION_ERROR = "无权限操作";
    public static final String THREAD_START_ERROR = "线程启动异常";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String LOCK_HAS_BIND_METTING_ERROR = "会议室预定信息不存在";
    public static final String RESERVE_NOT_EXIST_ERROR = "会议室预定信息不存在";
    public static final String METTING_HAS_NOT_LOCK_DEVICE_ERROR = "会议室未绑定门禁";
    public static final String ONLY_CAN_CANCEL_MYSELY_ERROR = "只能取消自己预约的信息";
    public static final String MOBILE_EXIST_ERROR = "手机号已存在";

    public static final String HAS_COMPANYWORK_INFO = "添加失败,已经添加过办公信息";
    public static final String TIME_HAS_RESERVE = "预定时间冲突";

    public static final Map<String,String> map = new HashMap<String,String>(){{
        // 开关
        put("open","01");
        put("close","00");
        put("stop","02");
        // 是否启用
        put("enable_true","01");
        put("enable_false","00");
        // 星期的
        put("Monday","2");
        put("Tuesday","3");
        put("Wednesday","4");
        put("Thursday","5");
        put("Friday","6");
        put("Saturday","7");
        put("Sunday","1");
    }};

    public static final Integer ONLINE_STATUS_ONLINE = 03;
    public static final Integer ONLINE_STATUS_OFFLINE = 00;

    public static final String GET_DEVICE_LIST = "0e 00 0c 00 08 00 08 00 ff ff ff ff fe 81";
//    public static final String GET_DEVICE_LIST = "08 00 ff ff ff ff fe 81";
}
