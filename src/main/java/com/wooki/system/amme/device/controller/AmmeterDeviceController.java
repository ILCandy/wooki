package com.wooki.system.amme.device.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.system.amme.ammeter.dto.IdAmDeviceDto;
import com.wooki.system.amme.device.dto.DeviceSetTypeDto;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.amme.device.service.AmmeterDeviceService;
import com.wooki.system.amme.expand.entity.DeviceExpand;
import com.wooki.system.amme.info.entity.AmmeterDayInfo;
import com.wooki.system.amme.info.service.AmmeterCurrentMonthService;
import com.wooki.system.amme.info.service.AmmeterDayInfoService;
import com.wooki.system.amme.info.service.AmmeterMonthInfoService;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.task.TimeInfo;
import com.wooki.util.common.date.DateUtil;
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
 * </p> *
 * @author whn
 * @since 2018-01-26
 */
@RestController
@RequestMapping("/ammeterDevice")
public class AmmeterDeviceController {

    @Autowired
    CompanyInfo companyInfo;

    @Autowired
    AmmeterDeviceService ammeterDeviceService;

    @Autowired
    AmmeterDayInfoService ammeterDayInfoService;

    @Autowired
    AmmeterMonthInfoService ammeterMonthInfoService;

    @Autowired
    AmmeterCurrentMonthService ammeterCurrentMonthService;

    /**
     * 对应公司的电表列表
     * @param request
     * @return
     */
    @GetMapping("/deviceList")
    public Object ammeterDevices(Page page, HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper deviceEW = new EntityWrapper();
        deviceEW.eq("company_id",company.getId());
        page = ammeterDeviceService.selectPage(page,deviceEW);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * 对应电表的详细信息
     * @return
     */
    @PostMapping("/deviceInfo")
    public Object ammeterInfo(@Valid @RequestBody IdAmDeviceDto idAmDeviceDto){
        Map map = new HashMap<>();
        // 电表
//        AmmeterDevice device = ammeterDeviceService.selectById(idAmDeviceDto.getId());
        AmmeterDevice device = ammeterDeviceService.selectByUuid(idAmDeviceDto.getId());
        // 电表用电的信息,总电量
        DeviceExpand deviceExpand = new DeviceExpand(idAmDeviceDto);
        // 昨日用电
        AmmeterDayInfo ammeterDayInfo = new AmmeterDayInfo(idAmDeviceDto.getId(), TimeInfo.getLastTimeStr());
        EntityWrapper dayInfoEW = new EntityWrapper(ammeterDayInfo);
        ammeterDayInfo = ammeterDayInfoService.selectOne(dayInfoEW);

        map.put("device",device);
        map.put("deviceExpand",deviceExpand);
        map.put("lastDay",ammeterDayInfo);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,map));
    }

    /**
     * 类型和总电量
     * @param request
     * @return
     */
    @GetMapping("/typeAndAllpower")
    public Object typeAndAllpower(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        List list = ammeterDeviceService.typeAndAllpower(company);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,list));
    }

    /**
     * 本月用电信息
     * @param request
     * @return
     */
    @GetMapping("/monthInfo")
    public Object ammeterMonthInfo(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper amdeEW = new EntityWrapper();
        amdeEW.setSqlSelect("Uuid");
        amdeEW.eq("company_id",company.getId());

        String timeStr = DateUtil.dateToStr(DateUtil.getCurrentMonthDay1TimeZero()).split(" ")[0];
        // 公司对电表列表
        List uuids = ammeterDeviceService.selectList(amdeEW);
        List infos = null;
        if(uuids!=null&&uuids.size()>0)
            infos = ammeterDayInfoService.selectMonthInfo(uuids,timeStr);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,infos));
    }

    /**
     * 最近12个月的信息
     * @param request
     * @return
     */
    @GetMapping("/everyMonthInfo")
    public Object ammeterEveryMonthInfo(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper amdeEW = new EntityWrapper();
        amdeEW.setSqlSelect("Uuid");
        amdeEW.eq("company_id",company.getId());

        // 公司对电表列表
        List uuids = ammeterDeviceService.selectList(amdeEW);
        List infos = null;
        if(uuids!=null&&uuids.size()>0)
            infos= ammeterMonthInfoService.selectEveryMonthInfo(uuids,Constant.nearMonthLimit);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,infos));
    }

    /**
     * 本月各设备
     * @param request
     * @return
     */
    @GetMapping("/everyDevice")
    public Object ammeterEveryDevice(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper amdeEW = new EntityWrapper();
        amdeEW.setSqlSelect("Uuid");
        amdeEW.eq("company_id",company.getId());

        List uuids = ammeterDeviceService.selectList(amdeEW);
        String timeStr = DateUtil.dateToStr(DateUtil.getCurrentMonthDay1TimeZero()).split(" ")[0];

        List deviceInfo = null;
        if(uuids!=null&&uuids.size()>0)
            deviceInfo = ammeterCurrentMonthService.selectEveryDevice(uuids);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,deviceInfo));
    }

    /**
     * 给电表设置类型
     * @param request
     * @param deviceSetTypeDto
     * @return
     */
    @PostMapping("/setType")
    public Object ammeterSetType(HttpServletRequest request,@Valid @RequestBody DeviceSetTypeDto deviceSetTypeDto){
        System.out.println("deviceSetTypeDto = "+deviceSetTypeDto);
        AmmeterDevice ammeterDevice = new AmmeterDevice(deviceSetTypeDto);
        ammeterDeviceService.updateByUuid(ammeterDevice);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }
	
}