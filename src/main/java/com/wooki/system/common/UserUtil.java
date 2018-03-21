package com.wooki.system.common;

import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.user.entity.TblUser;
import com.wooki.util.common.md5.MD5Util;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/7
 * Time : 下午1:40
 */
public class UserUtil {

    public static boolean checkUser(String password, TblUser user){
        if( user!=null && user.getPassword().equals(MD5Util.encode(password) ))
            return true;
        return false;
    }

    public static boolean checkCompany(String password, TblCompany company){
        if( company!=null && company.getPassword().equals(MD5Util.encode(password) ))
            return true;
        return false;
    }
}
