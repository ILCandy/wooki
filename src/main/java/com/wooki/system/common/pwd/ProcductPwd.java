package com.wooki.system.common.pwd;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/28
 * Time : 下午1:02
 */
public class ProcductPwd {
    public static Integer getPwd(){
        double pwd = (double)Math.random()*4000+(double)Math.random()*3000+(double)Math.random()*2000+(double)Math.random()*1000;
        return (int)pwd;
    }
}
