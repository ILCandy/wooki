package com.wooki.system.base;

import lombok.Data;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 17/10/25
 * Time : 上午10:55
 */
@Data
public class BaseJson {
    //信息
    private String msg;
    //代码
    private Integer code;
    //结果
    private Object data;

    public BaseJson(String msg,Integer code){
        this.msg = msg;
        this.code = code;
    }
    public BaseJson(String msg,Integer code,Object data){
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

//    private BaseJson init3(String msg,String flag,Object data){
//        this.data = data;
//        return init2()
//    }
//    public BaseJson init2(String msg,String flag){
//
//    }

    public static BaseJson ok(String msg){
        return new BaseJson(msg,200);
    }
    public static BaseJson ok(String msg,Object data){
        return new BaseJson(msg,200,data);
    }

    public static BaseJson fail(String msg){
        return new BaseJson(msg,450);
    }

    public static BaseJson fail(Integer code,String msg){
        return new BaseJson(msg,code);
    }
}
