package com.wooki.web;

import com.wooki.cache.CustomCache;
import com.wooki.function.exception.CustomException;
import com.wooki.system.base.Constant;
import com.wooki.system.base.ReturnEnum;
import com.wooki.system.common.Generate;
import com.wooki.system.common.UserUtil;
import com.wooki.system.common.redis.RedisTemplateUtils;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.company.mapper.TblCompanyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/7
 * Time : 上午1:17
 */
@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Autowired
    TblCompanyMapper companyMapper;

    @Autowired
    RedisTemplateUtils redisTemplateUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        // 接收报文
        if(uri.equals("/report/c")) return true;
        if(uri.length()<=1) uri ="/admin/login";
        if(uri.equals("/admin/login")||uri.equals("/tt"))
            return true;
        else {
            // 如果是登陆的话，验证账号密码
            if(uri.equals("/company/companyLogin")){
                String username = (String)request.getParameter("username");
                String password = (String)request.getParameter("password");
                String a = (String)request.getAttribute("username");
                String b = (String)request.getAttribute("password");
                // 账号密码不能为空
                if(StringUtils.isEmpty(username)|| StringUtils.isEmpty(password))
                    return false;
                // 以下进行账号密码验证
                TblCompany company = new TblCompany();
                company.setMobile(username);
                company = companyMapper.selectOne(company);
                System.out.println("username = "+username);
                System.out.println("passowrd  ="+password);
                // 密码认证成功
                if(UserUtil.checkCompany(password,company)){
                    String cacheToken = Generate.uuid();
//                    CustomCache.put(cacheToken,company);
                    redisTemplateUtils.setObject(cacheToken,company, Constant.SIX_HOUR);
                    Cookie cookie = new Cookie("token",cacheToken);
//                    cookie.setDomain("");
                    cookie.setPath("/");
                    response.addCookie(cookie);
//                    response.sendRedirect("index");
                    return true;
//                    return new ModelAndView("redirect:/index");
                }else {
                    // 账号密码错误
                    request.setAttribute("msg", ReturnEnum.PASS_OR_USER_ERROR.getMsg());
                    request.setAttribute("code", ReturnEnum.PASS_OR_USER_ERROR.getCode());
                    throw new Exception(ReturnEnum.PASS_OR_USER_ERROR.getMsg());
                }
                // 验证失败返回登录页面
//                else {} return false
            }
            // 如果是请求数据
            String token = request.getHeader("token");
            // 已经登录过，有token数据
            if(StringUtils.isNotEmpty(token))
//                if(CustomCache.get(token)!=null&&CustomCache.get(token)!=null)
                if(redisTemplateUtils.getObject(token)!=null)
                    return true;
            // token 不存在,返回信息
            throw new CustomException(ReturnEnum.LACK_TOKEN_ERROR);
        }
//        return true;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        return true;
//    }

    private void test(HttpServletResponse response){

        TblCompany company = companyMapper.selectById(1);
        String cacheToken = Generate.uuid();
        CustomCache.put(cacheToken,company);
        Cookie cookie = new Cookie("token",cacheToken);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
