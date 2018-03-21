package com.wooki.function.exception;

import com.wooki.system.base.ReturnEnum;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/21
 * Time : 下午4:32
 */
public class CustomException extends RuntimeException{

    private Integer code;
    private String msg;

    public CustomException(){
        super();
    }

    public CustomException(ReturnEnum returnEnum){
        super(returnEnum.getMsg());
        this.code = returnEnum.getCode();
        this.msg = returnEnum.getMsg();
    }

    public CustomException(String msg){
        super(msg);
        this.msg = msg;
    }

    public CustomException(String msg,Integer code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
