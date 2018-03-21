package com.wooki.system.amme.ammeter.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.function.annotation.Admin;
import com.wooki.system.amme.ammeter.dto.IdAmmeterDto;
import com.wooki.system.amme.ammeter.entity.Ammeter;
import com.wooki.system.amme.ammeter.service.AmmeterService;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.amme.device.service.AmmeterDeviceService;
import com.wooki.system.amme.info.service.AmmeterCurrentMonthService;
import com.wooki.system.amme.info.service.AmmeterDayInfoService;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.task.TimeInfo;
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
 * @since 2018-01-26
 */
@RestController
@RequestMapping("/ammeter")
public class AmmeterController {

    @Autowired
    AmmeterService ammeterService;

    @Autowired
    AmmeterDayInfoService ammeterDayInfoService;

    @Autowired
    AmmeterCurrentMonthService ammeterCurrentMonthService;

    @Autowired
    AmmeterDeviceService ammeterDeviceService;

    @Autowired
    AmmeterDeviceService deviceService;

    @Autowired
    CompanyInfo companyInfo;

    /**
     * 电表列表
     * @param page
     * @return
     */
    @Admin
    @GetMapping("/list")
    public Object ammeterList(Page page){
        page = deviceService.selectPage(page);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * 电表绑定公司
     * @param idAmmeterDto
     * @return
     */
    @Admin
    @PostMapping("/bindCompany")
    public Object bindCompany(@Valid @RequestBody IdAmmeterDto idAmmeterDto){
        Ammeter ammeter = new Ammeter(idAmmeterDto);
        AmmeterDevice device = new AmmeterDevice(idAmmeterDto);
        deviceService.updateByUuid(device);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 所属公司的电表设备
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/companyList")
    public Object companyList(Page page, HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("company_id",company.getId());
        page = deviceService.selectPage(page,entityWrapper);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }


    /**
     * 获取首页公司对用电量信息
     * @param request
     * @return
     */
    @GetMapping("/indexInfo")
    public Object getInfo(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        Map map = new HashMap<>();

        // lastDay
        EntityWrapper amdeEW = new EntityWrapper();
        amdeEW.setSqlSelect("Uuid");
        amdeEW.eq("company_id",company.getId());
        // 公司的电表 UUid 列表
        List Uuids = ammeterDeviceService.selectList(amdeEW);
        Double lastDayPower = 0.0;
        if(Uuids!=null&&Uuids.size()>0)
            lastDayPower = ammeterDayInfoService.lastDayInfo(Uuids, TimeInfo.getLastTimeStr());

        //currentMonth
        Double currentMonth = 0.0;
        if(Uuids!=null&&Uuids.size()>0)
            currentMonth = ammeterCurrentMonthService.currentMonthInfo(Uuids);

        map.put("lastDay",lastDayPower);
        map.put("currentMonth",currentMonth);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,map));
    }
}
