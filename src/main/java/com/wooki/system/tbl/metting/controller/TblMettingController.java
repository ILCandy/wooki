package com.wooki.system.tbl.metting.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.config.LockConfigurationProperties;
import com.wooki.function.annotation.Admin;
import com.wooki.function.annotation.BUser;
import com.wooki.function.annotation.CUser;
import com.wooki.function.auth.AuthMap;
import com.wooki.function.exception.CustomException;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.base.ReturnEnum;
import com.wooki.system.common.SimpleHttpClientDemo;
import com.wooki.system.common.oss.OssUpload;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.lock.entity.TblLockDevice;
import com.wooki.system.tbl.lock.service.TblLockDeviceService;
import com.wooki.system.tbl.metting.dto.*;
import com.wooki.system.tbl.metting.entity.TblMetting;
import com.wooki.system.tbl.metting.entity.TblMettingReserve;
import com.wooki.system.tbl.metting.service.TblMettingReserveService;
import com.wooki.system.tbl.metting.service.TblMettingService;
import com.wooki.task.thread.AmmeterHourThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-02-23
 */
@RestController
@RequestMapping("/metting")
public class TblMettingController {

    @Autowired
    CompanyInfo companyInfo;

    @Autowired
    LockConfigurationProperties lockConfigurationProperties;

    @Autowired
    TblMettingService mettingService;

    @Autowired
    TblLockDeviceService lockDeviceService;

    @Autowired
    TblMettingReserveService mettingReserveService;

    @Autowired
    OssUpload ossUpload;

    /**
     * 会议室添加
     * @param request
     * @param mettingAddDto
     * @return
     */
    @Admin
	@PostMapping("/add")
    public Object addMetting(HttpServletRequest request,@Valid @RequestBody MettingAddDto mettingAddDto){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblMetting metting = new TblMetting(company,mettingAddDto);
        mettingService.insert(metting);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,metting));
    }

    /**
     * 管理员将会议室绑定到b用户上
     * @param request
     * @param mettingSetBUserDto
     * @return
     */
    @Admin
    @PostMapping("/mettingSetBUser")
    public Object mettingSetBUser(HttpServletRequest request,@Valid @RequestBody MettingSetBUserDto mettingSetBUserDto){
        TblMetting metting = new TblMetting(mettingSetBUserDto);
        mettingService.updateById(metting);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,metting));
    }

    /**
     * 会议室修改
     * @param request
     * @param mettingUpdateDto
     * @return
     */
    @BUser
    @PostMapping("/update")
    public Object updateMetting(HttpServletRequest request,@Valid @RequestBody MettingUpdateDto mettingUpdateDto){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblMetting metting = new TblMetting(mettingUpdateDto);
        mettingService.updateById(metting);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,metting));
    }

    /**
     * 会议室列表 ( b，c用户 )
     * @param request
     * @param page
     * @return
     */
    @CUser
    @GetMapping("/list")
    public Object mettingList(HttpServletRequest request,Page page){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper mettingEW = new EntityWrapper();

        Integer checkId = 0;
         // 如果是b用户，查看自己添加的
        if(company.getType().equals(AuthMap.getAuth(Constant.BUser)))
            checkId = company.getId();
        else if(company.getType().equals(AuthMap.getAuth(Constant.CUser))) {
            // 如果是c用户，查看自己上级b用户的会议室
            checkId = company.getSuperId();
        }

        mettingEW.eq("company_id",checkId);
        page = mettingService.selectPage(page,mettingEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * 管理员的会议室列表
     * @param request
     * @param page
     * @return
     */
    @Admin
    @GetMapping("/adminList")
    public Object adminList(HttpServletRequest request,Page page){
        EntityWrapper mettingEW = new EntityWrapper();

        page = mettingService.selectPage(page,mettingEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }


    /**
     * 会议室设定门禁
     * @param mettingSetSwitchDto
     * @return
     */
    @BUser
    @PostMapping("/setSwitch")
    public Object setSwitch(@Valid @RequestBody MettingSetSwitchDto mettingSetSwitchDto){

        EntityWrapper checkLockEW = new EntityWrapper();
        checkLockEW.eq("switch_id",mettingSetSwitchDto.getSwitchId());
        // 门禁已经绑定会议室
        if(mettingService.selectOne(checkLockEW)!=null)
            throw new CustomException(ReturnEnum.LOCK_HAS_BIND_METTING_ERROR);
        TblMetting metting = new TblMetting(mettingSetSwitchDto);
        mettingService.updateById(metting);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,metting));
    }

    /**
     * 会议室预定
     * @param metthingReserveDto
     * @return
     */
    @CUser
    @PostMapping("/reserve")
    public Object reserveMetting(HttpServletRequest request,@Valid @RequestBody MettingReserveDto metthingReserveDto)throws Exception {
        // 申请的公司
        TblCompany company = companyInfo.getRedisCompany(request);
//        Date beginTime = metthingReserveDto.getBeginDate();
//        Date endTime = metthingReserveDto.getEndDate();
        String beginTime = metthingReserveDto.getBeginDate();
        String endTime = metthingReserveDto.getEndDate();

        String mettingId = metthingReserveDto.getId();

        // 会议室信息
        TblMetting metting = mettingService.selectById(mettingId);
        if(metting.getSwitchId()==null)
            throw new CustomException(ReturnEnum.METTING_HAS_NOT_LOCK_DEVICE_ERROR);
        // 门禁信息
        TblLockDevice lockDevice = lockDeviceService.selectById(metting.getSwitchId());

        // 基本信息
//        Map paramMap = new HashMap<>(10);

//        paramMap.put(Constant.LOCK_ACCESS_TOKEN, LockConfigurationProperties.getLockAccesToken());
//        paramMap.put(Constant.LOCK_OPERATION, lockConfigurationProperties.getMethodAdd());

        // data
//        Map data = new HashMap(15);
//        data.put("dev_sn",lockDevice.getDevSn());
//        data.put("start_datetime",beginTime.getTime());
//        data.put("end_datetime",endTime.getTime());
//        data.put("use_count",0); // 无数次

//        paramMap.put(Constant.LOW_KEY_DATA, data);

        String paramStr = "{\"access_token\":\"b372c792621d576179f2163ab194489edf5b8966cb2977aL\"," +
                " \"data\":" +
                "{\"dev_sn_list\":[\""+lockDevice.getDevSn() +"\"]"+
                ",\"start_datetime\":\""+beginTime+"\"" +
                ",\"end_datetime\":\""+endTime+"\"" +
                ",\"use_count\":"+0+"" +
                ",\"pwd_type\":"+2+"}" + // pwd_type = 2 -> 二维码, 1是数字
                ", \"operation\":\"POST\"}";
        System.out.println("预约 :"+paramStr);

        // 返回的result 为json 字符串
        // 设定临时密码的返回信息
        String result = SimpleHttpClientDemo.lockPost(
                lockConfigurationProperties.getBaseUrl()+lockConfigurationProperties.getTempPwdUrl(),
                paramStr,
                lockConfigurationProperties.getEncode()
        );
        // 解析结果
        HashMap resultMap = JSON.parseObject(result,HashMap.class);

        // 如果成功
        if(((String)resultMap.get(Constant.RETURN_MSG)).equals(Constant.OK)) {
            TblMettingReserve mettingReserve = new TblMettingReserve();
            mettingReserve.setCompanyId(company.getId());
            mettingReserve.setCompanyName(company.getName());
            mettingReserve.setSuperId(company.getSuperId());
//            mettingReserve.setId((Long) resultMap.get("id"));
//            mettingReserve.setTempPwd((String)resultMap.get(Constant.LOCK_TEMP_PWD));
//            mettingReserve.setQrcode((String)resultMap.get(Constant.LOCK_TEMP_PWD_QRCORD));
            String url = ossUpload.uploadByBase64Str((String)resultMap.get(Constant.LOCK_TEMP_PWD_QRCORD));
            mettingReserve.setPicUrl(url);
            mettingReserve.setQrcodeContent((String)resultMap.get(Constant.LOCK_TEMP_PWD_QRCORD_NUMBER));
            mettingReserve.setBeginTime(beginTime);
            mettingReserve.setEndTime(endTime);
            mettingReserve.setCreateTime(new Date());
            mettingReserve.setDeviceId(metting.getSwitchId());
            mettingReserve.setStatus(Constant.RESERVE_TRUE);// 默认启动
            // 新增字段
            mettingReserve.setMettingId(mettingId);
            mettingReserve.setMettingName(metting.getName());
            mettingReserveService.insert(mettingReserve);
            return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,mettingReserve));
        }
        return JSON.toJSON(BaseJson.fail(Constant.OPERATE_FAIL));
    }

    /**
     * 取消预约
     * @param request
     * @param mettingUnReserveDto
     * @return
     * @throws IOException
     */
    @CUser
    @PostMapping("/unReserve")
    public Object unReserveMetting(HttpServletRequest request,@Valid @RequestBody MettingUnReserveDto mettingUnReserveDto)throws IOException {

        TblCompany company = companyInfo.getRedisCompany(request);
        // 预定信息的id
        Long id = mettingUnReserveDto.getId();
        TblMettingReserve mettingReserve = mettingReserveService.selectById(id);
        TblLockDevice lockDevice = lockDeviceService.selectById(mettingReserve.getDeviceId());

        // 如果不是自己预约的，无法取消
        if(!mettingReserve.getCompanyId().equals(company.getId())){
            throw new CustomException(ReturnEnum.ONLY_CAN_CANCEL_MYSELY_ERROR);
        }
        String paramStr = "{\"access_token\":\"b372c792621d576179f2163ab194489edf5b8966cb2977aL\"," +
                " \"data\":"+
                "{\"dev_sn\":\""+lockDevice.getDevSn() +"\""+
                ",\"temp_pwd\":\""+mettingReserve.getQrcodeContent()+"\""+
                "}, \"operation\":\"DELETE\"}";

        // 返回的result 为json 字符串
        String result = SimpleHttpClientDemo.lockPost(
                lockConfigurationProperties.getBaseUrl()+lockConfigurationProperties.getTempPwdUrl(),
                paramStr,
                lockConfigurationProperties.getEncode()
        );
        // 解析结果
        HashMap resultMap = JSON.parseObject(result,HashMap.class);

        // 如果成功
        if(((String)resultMap.get(Constant.RETURN_MSG)).equals(Constant.OK)) {
            mettingReserve.setStatus(Constant.METTING_RESERVE_FALSE);
            mettingReserveService.updateById(mettingReserve);
            return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
        }
        return JSON.toJSON(BaseJson.fail(Constant.OPERATE_FAIL));

    }

    /**
     * b 和 c 用户的预定列表
     * @param request
     * @param page
     * @return
     */
    @CUser
    @GetMapping("/reserveList")
    public Object bReserveList(HttpServletRequest request,Page page){
        TblCompany company = companyInfo.getRedisCompany(request);
        EntityWrapper selectEW = new EntityWrapper();
        Integer checkId = 0;
        if(company.getType().equals(AuthMap.getAuth(Constant.BUser)))
            checkId = company.getId();
        else if(company.getType().equals(AuthMap.getAuth(Constant.CUser)))
            checkId = company.getSuperId();
        selectEW
                .eq("super_id",checkId)
                .orderBy("create_time",false);
        mettingReserveService.selectPage(page,selectEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    /**
     * c用户的预约列表
     * @param request
     * @param page
     * @return
     */
//    @CUser
//    @GetMapping("/reserveList")
//    public Object cReserveList(HttpServletRequest request,Page page){
//        TblCompany company = companyInfo.getRedisCompany(request);
//        EntityWrapper selectEW = new EntityWrapper();
//        selectEW
//                .eq("super_id",company.getSuperId())
//                .orderBy("create_time",false);
//        mettingReserveService.selectPage(page,selectEW);
//        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
//    }

    @Admin
    @GetMapping("/amme")
    public Object ame(){
        new AmmeterHourThread().start();
        return "测试成功";
    }
}
