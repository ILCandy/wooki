package com.wooki.cache;

import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.entity.TblCompany;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/7
 * Time : 下午1:19
 */
public class CustomCache {
    public static ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();

    public static ConcurrentHashMap put(String key,Object value){
        map.put(key,value);
        return map;
    }

    public static Object get(String key){
        return map.get(key);
    }

    public static String getToken(HttpServletRequest request){
        return (String)request.getHeader(Constant.HEADER_KEY_TOKEN);
    }

    public static TblCompany getCompanyByRequest(HttpServletRequest request){
        return (TblCompany)map.get(getToken(request));
    }
}
