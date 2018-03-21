package com.wooki.system.common.redis.amme.day;

import com.wooki.system.base.Constant;
import com.wooki.system.common.redis.KeyPack;
import com.wooki.system.common.redis.RedisTemplateUtils;
import com.wooki.system.tbl.company.entity.TblCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/31
 * Time : 上午12:31
 */
@Component
public class CacheAmmeterDayInfo {

    @Autowired
    RedisTemplateUtils redisTemplateUtils;

    // 默认是当前天
    public void setDayInfo(TblCompany company){
        redisTemplateUtils.setObject(KeyPack.keyComDayInfo(company),company, Constant.ONE_DAY);
    }

    // 默认是当前天
    public <T> T getDayInfo(TblCompany company,Class<T> clazz){
        return (T) redisTemplateUtils.get(KeyPack.keyComDayInfo(company));
    }

    // 对应日期
    public void setDayInfo(TblCompany company,String timeStr){
        redisTemplateUtils.setObject(KeyPack.keyComDayInfo(company,timeStr),company, Constant.ONE_DAY);
    }
    // 对应日期
    public <T> T getDayInfo(TblCompany company,String timeStr,Class<T> clazz){
        return (T) redisTemplateUtils.get(KeyPack.keyComDayInfo(company,timeStr));
    }


}
