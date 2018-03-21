package com.wooki.system.tbl.company.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.function.annotation.Admin;
import com.wooki.function.auth.AuthMap;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.dto.AddCompanyDto;
import com.wooki.system.tbl.company.dto.BSetSuperDto;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.company.service.TblCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@RestController
@RequestMapping("/company")
public class TblCompanyController {

    @Autowired
    TblCompanyService companyService;
    @Autowired
    CompanyInfo companyInfo;

    @RequestMapping("/companyLogin")
    public Object userLogin(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        String msg = null;
        if(company.getType().equals(AuthMap.getAuth(Constant.ADMIN)))
            msg = "admin";
        else if(company.getType().equals(AuthMap.getAuth(Constant.BUser)))
            msg = "buser";
        else if(company.getType().equals(AuthMap.getAuth(Constant.CUser)))
            msg = "cuser";
        return JSON.toJSON(BaseJson.ok(msg));
    }

    /**
     * 公司信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public Object companyInfo(HttpServletRequest request){
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, companyInfo.getRedisCompany(request)));
    }

    /**
     * 公司列表
     * @param page 分页参数
     * @return
     */
    @Admin
    @GetMapping("/list")
    public Object companyList(Page page){
        page = companyService.selectPage(page);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, page));
    }

    /**
     * b用户列表
     * @param page
     * @return
     */
    @Admin
    @GetMapping("/bList")
    public Object bList(Page page){
        EntityWrapper bEW = new EntityWrapper();
        bEW.eq("type", AuthMap.getAuth(Constant.BUser));
        page = companyService.selectPage(page,bEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, page));
    }

    /**
     * c用户列表
     * @param page
     * @return
     */
    @Admin
    @GetMapping("/cList")
    public Object cList(Page page){
        EntityWrapper cEW = new EntityWrapper();
        cEW.eq("type",AuthMap.getAuth(Constant.CUser));
        page = companyService.selectPage(page,cEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, page));
    }

    /**
     * 添加公司
     * @param request
     * @param addCompanyDto
     * @return
     */
    @Admin
    @PostMapping("/add")
    public Object companyAdd(HttpServletRequest request, @Valid @RequestBody AddCompanyDto addCompanyDto){
        if(!checkPhone(addCompanyDto.getMobile()))
            return JSON.toJSON(BaseJson.fail(Constant.MOBILE_EXIST_ERROR));
        TblCompany company = new TblCompany(addCompanyDto);
        companyService.insert(company);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    private boolean checkPhone(String modile){
        Map map = new HashMap<>(3);
        map.put("mobile",modile);
        List checkList = companyService.selectByMap(map);
        System.out.println("checkList = "+checkList);
        if(checkList!=null&&checkList.size()>0) {
            System.out.println("手机号已存在");
            return false;
        }
        return true;
    }

    /**
     * 给c用户设定上级b用户
     * @param request
     * @param bSetSuperDto
     * @return
     */
    @Admin
    @PostMapping("/bSetSuper")
    public Object bSetSuperCompany(HttpServletRequest request,@Valid @RequestBody BSetSuperDto bSetSuperDto){
        TblCompany company = new TblCompany();
        company.setId(bSetSuperDto.getChildCompanyId());
        company.setSuperId(bSetSuperDto.getSuperCompanyId());
        companyService.updateById(company);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

}
