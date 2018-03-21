package com.wooki.function.aspect;

import com.wooki.function.exception.CustomException;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.Constant;
import com.wooki.system.base.ReturnEnum;
import com.wooki.function.auth.AuthMap;
import com.wooki.system.tbl.company.entity.TblCompany;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/22
 * Time : 下午6:24
 */
@Aspect
@Component
public class CUserCheckAspect {

    @Autowired
    CompanyInfo companyInfo;

    @Pointcut("@annotation(com.wooki.function.annotation.BUser)")
    public void checkBUser(){}

    @Before("checkBUser()")
    public void check(){

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_KEY_TOKEN);

        TblCompany company = companyInfo.getRedisCompany(request);
        // 若权限小于 C 用户，抛出异常
        if(company.getType()==null||company.getType()< AuthMap.getAuth(Constant.CUser))
            throw new CustomException(ReturnEnum.LACK_TOKEN_NO_PERMISSION);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("=========================");
//    }
}
