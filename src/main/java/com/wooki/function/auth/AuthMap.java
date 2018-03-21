package com.wooki.function.auth;

import com.wooki.system.base.Constant;

import java.util.HashMap;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/3/10
 * Time : 上午11:35
 */
public class AuthMap {
    private static final HashMap map = new HashMap(){{
        put(Constant.ADMIN,1000);
        put(Constant.BUser,500);
        put(Constant.CUser,400);
    }};

    private static final HashMap getMap() {
        return map;
    }

    public static final Integer getAuth(String auth){
        return (Integer)getMap().get(auth);
    }
}
