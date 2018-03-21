package com.wooki.system.analy;

import com.wooki.cache.CustomCache;
import com.wooki.system.common.redis.RedisTemplateUtils;
import com.wooki.system.tbl.company.entity.TblCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/10
 * Time : 上午10:10
 */
@Component
public class CompanyInfo {

    @Autowired
    RedisTemplateUtils redisTemplateUtils;

    private static boolean isPro = false;

    @Value("${isPro}")
    public void setPro(boolean flag){
        isPro = flag;
    }

    public static TblCompany getCompany(HttpServletRequest request){
        TblCompany company ;
        if(isPro) {
            company = CustomCache.getCompanyByRequest(request);
        }else {
            company = new TblCompany();
            company.setId(1);
        }
        return company;
    }

    public TblCompany getRedisCompany(HttpServletRequest request){
        return redisTemplateUtils.getObject(CustomCache.getToken(request),TblCompany.class);
    }
}
