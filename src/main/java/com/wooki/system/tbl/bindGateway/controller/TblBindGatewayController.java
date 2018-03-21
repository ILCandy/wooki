package com.wooki.system.tbl.bindGateway.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.function.annotation.Admin;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.bindGateway.dto.BindGayewayDto;
import com.wooki.system.tbl.bindGateway.dto.IdBindGatewayDto;
import com.wooki.system.tbl.bindGateway.entity.TblBindGateway;
import com.wooki.system.tbl.bindGateway.service.TblBindGatewayService;
import com.wooki.system.tbl.company.dto.IdCompanyDto;
import com.wooki.system.tbl.company.service.TblCompanyService;
import com.wooki.system.tbl.gateway.service.TblGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@RestController
@RequestMapping("/bindGateway")
public class TblBindGatewayController {

    @Autowired
    TblBindGatewayService bindGatewayService;

    @Autowired
    TblCompanyService companyService;

    @Autowired
    TblGatewayService gatewayService;

    /**
     * 网关绑定公司
     * @param request
     * @param bindGayewayDto
     * @return
     */
    @Admin
    @PostMapping("/bind")
    public Object companyBindGateway(HttpServletRequest request, @Valid @RequestBody BindGayewayDto bindGayewayDto){
        TblBindGateway bindGateway = new TblBindGateway(bindGayewayDto);
        bindGateway.setAddTime(new Date());
        bindGatewayService.insert(bindGateway);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 所有绑定的列表
     * @param request
     * @return
     */
    @Admin
    @GetMapping("/list")
    public Object bindGatewayList(HttpServletRequest request,Page page){
        page.setOrderByField("company_id");
        page = bindGatewayService.selectPage(page);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * 对应公司绑定的网关列表
     * @param request
     * @param page
     * @return
     */
    @Admin
    @PostMapping("/companyList")
    public Object companyGatewayList(HttpServletRequest request, Page page, @Valid @RequestBody IdCompanyDto idCompanyDto){
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("company_id",idCompanyDto.getId());
        page.setOrderByField("company_id");
        page = bindGatewayService.selectPage(page,entityWrapper);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * 删除绑定信息
     * @param request
     * @return
     */
    @Admin
    @PostMapping("/delete")
    public Object companyDeleteGateway(HttpServletRequest request, @Valid @RequestBody IdBindGatewayDto idBindGatewayDto){
        bindGatewayService.deleteById(idBindGatewayDto.getId());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }
}
