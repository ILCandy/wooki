package com.wooki.system.tbl.lock.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.config.LockConfigurationProperties;
import com.wooki.function.annotation.Admin;
import com.wooki.function.annotation.BUser;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.common.SimpleHttpClientDemo;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.lock.dto.LockSetCompanyDto;
import com.wooki.system.tbl.lock.dto.TblLockDeviceDto;
import com.wooki.system.tbl.lock.entity.TblLockDevice;
import com.wooki.system.tbl.lock.service.TblLockDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-02-28
 * 这特么什么垃圾接口，直接返回数据库字段给你
 */
@RestController
@RequestMapping("/lock")
public class TblLockDeviceController {

    @Autowired
    CompanyInfo companyInfo;

    @Autowired
    LockConfigurationProperties lockConfigurationProperties;

    @Autowired
    TblLockDeviceService lockDeviceService;

    /**
     * 门禁绑定公司
     * @param lockSetCompanyDto
     * @return
     */
    @Admin
    @PostMapping("/lockSetCompany")
    public Object lockSetCompany(@Valid @RequestBody LockSetCompanyDto lockSetCompanyDto){
        TblLockDevice lockDevice = new TblLockDevice(lockSetCompanyDto);
        lockDeviceService.updateById(lockDevice);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }
    /**
     * 门禁列表
     * @param request
     * @param page
     * @return
     */
    @Admin
    @GetMapping("/deviceList")
    public Object deviceList(HttpServletRequest request,Page page){
        page = lockDeviceService.selectPage(page);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * B用户的门禁列表
     * @param request
     * @param page
     * @return
     */
    @BUser
    @GetMapping("/deviceListB")
    public Object deviceListB(HttpServletRequest request,Page page){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper bEW = new EntityWrapper();
        bEW.eq("company_id",company.getId());
        page = lockDeviceService.selectPage(page,bEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * 刷新门禁列表
     * @return
     */
    @Admin
    @GetMapping("/refreshLockList")
    public Object refreshLockList()throws IOException{

        // 参数
//        Map paramMap = new HashMap<>(10);
//        paramMap.put(Constant.LOCK_ACCESS_TOKEN, LockConfigurationProperties.getLockAccesToken());
//        paramMap.put(Constant.LOCK_OPERATION, lockConfigurationProperties.getMethodGet());

        String paramStr = "{\"access_token\":\"b372c792621d576179f2163ab194489edf5b8966cb2977aL\", \"data\":{}, \"operation\":\"GET\"}";
        System.out.println("设备列表 :"+paramStr);
        // 请求条件
//        Map data = new HashMap(10);
        // 这里直接获取所有的. 如果你有超过整形最大的门禁数量的话，那就另当别论
//        data.put(Constant.PAGE_OFFSET,0);
//        data.put(Constant.PAGE_LIMIT,Integer.MAX_VALUE);
//        data.put(Constant.LOW_KEY_DATA,data);

        String result = SimpleHttpClientDemo.lockPost(
                lockConfigurationProperties.getBaseUrl()+lockConfigurationProperties.getLockListUrl(),
                paramStr,
                lockConfigurationProperties.getEncode()
        );
//        // 获取到data
        HashMap resultMap = JSON.parseObject(result,HashMap.class);
        // to array
        JSONArray array = (JSONArray)resultMap.get(Constant.LOW_KEY_DATA);
        // toList about lockDevice
        List<TblLockDeviceDto> lockDeviceDtoList = array.toJavaList(TblLockDeviceDto.class);

        List<TblLockDevice> lockDeviceList = new ArrayList<>();
        TblLockDeviceDto lockDeviceDto;
        TblLockDevice lockDevice;
        for(int i = 0;i<lockDeviceDtoList.size();i++){
            lockDeviceDto = lockDeviceDtoList.get(i);
            lockDevice = new TblLockDevice();
            lockDevice.setId(lockDeviceDto.getId());
            lockDevice.setDevName(lockDeviceDto.getDev_name());
            lockDevice.setDevSn(lockDeviceDto.getDev_sn());
            lockDevice.setDevType(lockDeviceDto.getDev_type());
            lockDevice.setDoorNo(lockDeviceDto.getDoor_no());
            lockDevice.setFloorCount(lockDeviceDto.getFloor_count());
            lockDevice.setSection(lockDeviceDto.getSection());
            lockDevice.setSectionKey(lockDeviceDto.getSection_key());
//            lockDevice.set
            lockDeviceList.add(lockDevice);
        }
//        System.out.println("lockDeviceList = "+lockDeviceList);
        if(lockDeviceList!=null&&lockDeviceList.size()>0)
            lockDeviceService.updateLockDevice(lockDeviceList);
//
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }
}
