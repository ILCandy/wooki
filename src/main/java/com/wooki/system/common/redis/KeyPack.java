package com.wooki.system.common.redis;

import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.task.TimeInfo;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/31
 * Time : 上午12:30
 */
public class KeyPack {

    // key : 2018-1-31_1
    public static String keyComDayInfo(TblCompany company){
//        return TimeInfo.getTimeStr()+"_"+company.getId();
        return keyComDayInfo(company,TimeInfo.getTimeStr());
    }

    public static String keyComDayInfo(TblCompany company,String timeStr){
        return timeStr+"_"+company.getId();
    }

}
