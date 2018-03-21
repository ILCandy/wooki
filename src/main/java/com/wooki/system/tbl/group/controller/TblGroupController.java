package com.wooki.system.tbl.group.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.device.entity.GroupOnline;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.group.dto.GroupDeviceDto;
import com.wooki.system.tbl.group.entity.TblGroup;
import com.wooki.system.tbl.group.service.TblGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-01-08
 */
@RestController
@RequestMapping("/group")
public class TblGroupController {

    @Autowired
    TblGroupService groupService;

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    CompanyInfo companyInfo;

    /**
     * 分组列表
     * @param request
     * @return
     */
    @GetMapping("/list")
    public Object groupList(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblGroup group = new TblGroup();
        group.setCompanyId(company.getId());
        List groups = groupService.selectList(new EntityWrapper<>(group));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,groups));
    }

    /**
     * 根据分组id获取设备信息
     * @param groupDeviceDto id ,分组id
     * @return
     */
    @PostMapping("/device")
    public Object groupList(@Valid @RequestBody GroupDeviceDto groupDeviceDto){
        TblDevice device = new TblDevice();
        device.setGroupId(groupDeviceDto.getGroupId());
        List devices = deviceService.selectList(new EntityWrapper<>(device));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,devices));
    }

    /**
     * 分组的在线数
     * @return
     */
    @GetMapping("/onlineCount")
    public Object onlineCount(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);

        // 分组列表
        TblGroup group = new TblGroup();
        group.setCompanyId(company.getId());
        List<TblGroup> groups = groupService.selectList(new EntityWrapper<>(group));

        // 根据分组查询出来的结果
        TblDevice device = new TblDevice();
        device.setCompanyId(company.getId());
        device.setOnline(Constant.ONLINE_STATUS_ONLINE);
        List<GroupOnline> onlineList = deviceService.selectGroupOnline(device);

        onlineList = transGroup(groups,onlineList);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,onlineList));
    }

    // 设备中无的设置为0
    private List transGroup(List<TblGroup> groups,List<GroupOnline> groupOnlines){
        if(groups==null||groups.size()==0)
            return groupOnlines;
        GroupOnline groupOnline ;
        TblGroup group;
        boolean flag;
        for(int i = 0;i<groups.size();i++){
            group = groups.get(i);
            flag = false;
            for(int j = 0;j<groupOnlines.size();j++){
                if(group.getId().equals(groupOnlines.get(j).getGroupId())){
                    flag = true;
                    break;
                }
            }
            // 如果分组中没有
            if(!flag){
                groupOnline = new GroupOnline();
                groupOnline.setGroupId(group.getId());
                groupOnline.setCount(0);
                groupOnlines.add(groupOnline);
            }
        }
        return groupOnlines;
    }

    /**
     * 添加分组
     * @param group name 分组名
     * @return
     */
    @PostMapping("/add")
    public Object addGroup(HttpServletRequest request,@RequestBody TblGroup group){
        group.setCreateTime(new Date());
        group.setCompanyId(companyInfo.getRedisCompany(request).getId());
        groupService.insert(group);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,group));
    }

    /**
     * 修改分组信息
     * @param group id，分组id，name，分组名
     * @return
     */
    @PostMapping("/update")
    public Object updateGroup(@RequestBody TblGroup group){
        group.setUpdateTime(new Date());
        groupService.updateById(group);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 删除分组
     * @param group
     * @return
     */
    @PostMapping("/delete")
    public Object update(@RequestBody TblGroup group){
        if(groupService.deleteGroup(group))
            return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
        return JSON.toJSON(BaseJson.fail(Constant.OPERATE_FAIL));
    }

}
