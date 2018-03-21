package com.wooki.system.common;

import java.util.UUID;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/7
 * Time : 上午11:51
 */
public class Generate {

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
