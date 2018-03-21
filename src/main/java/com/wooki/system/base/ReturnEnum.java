package com.wooki.system.base;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/7
 * Time : 下午2:46
 */
public enum  ReturnEnum{

    PASS_OR_USER_ERROR(Constant.USERNAME_PASSWORD_ERROR,Constant.USERNAME_PASSWORD_ERROR_CODE),
    LACK_TOKEN_ERROR(Constant.LACK_TOKEN_ERROR,Constant.LACK_TOKEN_ERROR_CODE),
    LACK_TOKEN_NO_PERMISSION(Constant.NO_PERMISSION_ERROR,Constant.NO_PERMISSION_CODE),
    THREAD_START_ERROR(Constant.THREAD_START_ERROR,Constant.THREAD_START_ERROR_CODE),
    PASSWORD_ERROR(Constant.PASSWORD_ERROR,Constant.PASSWORD_ERROR_CODE),
    RESERVE_NOT_EXIST_ERROR(Constant.RESERVE_NOT_EXIST_ERROR,Constant.RESERVE_NOT_EXIST_ERROR_CODE),
    LOCK_HAS_BIND_METTING_ERROR(Constant.LOCK_HAS_BIND_METTING_ERROR,Constant.LOCK_HAS_BIND_METTING_ERROR_CODE),
    ONLY_CAN_CANCEL_MYSELY_ERROR(Constant.ONLY_CAN_CANCEL_MYSELY_ERROR,Constant.ONLY_CAN_CANCEL_MYSELY_ERROR_CODE),
    METTING_HAS_NOT_LOCK_DEVICE_ERROR(Constant.METTING_HAS_NOT_LOCK_DEVICE_ERROR,Constant.METTING_HAS_NOT_LOCK_DEVICE_CODE),
    MOBILE_EXIST_ERROR(Constant.ONLY_CAN_CANCEL_MYSELY_ERROR,Constant.ONLY_CAN_CANCEL_MYSELY_ERROR_CODE);


    private String msg;
    private Integer code;

    private ReturnEnum(String msg,Integer code){
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
