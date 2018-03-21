package com.wooki.system.tbl.link.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.wooki.function.keypack.SelectKeyPack;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.link.dto.LinkAddDto;
import com.wooki.system.tbl.link.dto.LinkDeleteDto;
import com.wooki.system.tbl.link.dto.LinkUpdateDto;
import com.wooki.system.tbl.link.entity.TblLink;
import com.wooki.system.tbl.link.service.TblLinkService;
import com.wooki.util.common.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-01-24
 */
@RestController
@RequestMapping("/link")
public class TblLinkController {

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    TblLinkService linkService;

    @Autowired
    CompanyInfo companyInfo;

    /**
     * 联动的列表
     * @param request
     * @return
     */
    @GetMapping(value="/list")
    public Object linkList(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblLink link = new TblLink();
        link.setCompanyId(company.getId());
        List linkList = linkService.selectList(new EntityWrapper<>(link));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,linkList));
    }

    /**
     * 添加联动
     * @param linkAddDto deviceId,time,linkList-> [1:open,2:close]
     * @return
     */
    @PostMapping(value ="/add")
    public Object addLink(HttpServletRequest request,@Valid @RequestBody LinkAddDto linkAddDto){
        TblCompany company = companyInfo.getRedisCompany(request);
        // 设备
        TblDevice device = deviceService.selectById(linkAddDto.getDeviceId());
        // 联动列表
        String linkList = getLinkList(linkAddDto.getLinkList());

        String time = operaTime(linkAddDto.getTime());
        TblLink link = new TblLink();
        link.setTime(time);
        link.setCompanyId(company.getId());
        // 设备短地址
        link.setShortAddr(device.getShortAddr());
        link.setEndPoint(device.getEndPoint());
        link.setLinkTime(linkAddDto.getTime());
        link.setStatus(linkAddDto.getStatus());
        // 联动的设备 id:status ->  1:1
        link.setDeviceStatus(linkList);
        link.setAddtime(new Date());
        link.setDays(DateUtil.getDaysToNumStr(linkAddDto.getDays()));
        link.setSelectKey(SelectKeyPack.getLinkSelectKey(link));

        linkService.insert(link);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,link.getId()));
    }

    private String operaTime(String originTime){
        StringBuffer stringBuffer = new StringBuffer();
        String[] timeLenArr = originTime.split(",");
        String startStr=null;
        String endStr=null;
        for(int i = 0;i<timeLenArr.length;i++){
            String [] timeArr = timeLenArr[i].split(Constant.TIMESPLITSTR);
            startStr = "2017-01-01 "+timeArr[0];
            endStr = "2017-01-01 "+timeArr[1];
            stringBuffer.append(DateUtil.taskTime(startStr));
            stringBuffer.append(Constant.TIMESPLITSTR);
            stringBuffer.append(DateUtil.taskTime(endStr));
            if(i<timeLenArr.length-1)
                stringBuffer.append(",");
        }
        return stringBuffer.toString();
    }

    /**
     * 更新联动信息
     * @param linkUpdateDto id,
     * @return
     */
    @PostMapping(value ="/update")
    public Object updateLink(@Valid @RequestBody LinkUpdateDto linkUpdateDto) {
        TblLink link = new TblLink();
        link.setId(linkUpdateDto.getId());
        if(StringUtils.isNotEmpty(linkUpdateDto.getDeviceStatus())) {
            link.setDeviceStatus(getLinkList(linkUpdateDto.getDeviceStatus()));
            link.setSelectKey(SelectKeyPack.getLinkSelectKey(link));
        }
        if(StringUtils.isNotEmpty(linkUpdateDto.getTime()))
            link.setLinkTime(linkUpdateDto.getTime());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 删除
     * @param linkDeleteDto
     * @return
     */
    @PostMapping(value ="/delete")
    public Object deleteLink(@Valid @RequestBody LinkDeleteDto linkDeleteDto) {
        linkService.deleteById(linkDeleteDto.getId());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    private String getLinkList(String linkList){
        linkList = linkList.replaceAll("open", Constant.map.get("open"));
        linkList = linkList.replaceAll("close", Constant.map.get("close"));
        return linkList;
    }

    /**
     * 详情
     * @param request
     * @param linkDeleteDto
     * @return
     */
    @PostMapping("/detailInfo")
    public Object deviceList(HttpServletRequest request, @Valid @RequestBody LinkDeleteDto linkDeleteDto){
        TblLink link = linkService.selectById(linkDeleteDto.getId());
        String deviceStatus = link.getDeviceStatus();// 1:1,2:0
        String[] deviceStatusList = deviceStatus.split(",");
        List<Integer> ids = new ArrayList<>();
        for(String device:deviceStatusList){
            ids.add(Integer.valueOf(device.split(":")[0]));
        }

        // 短地址和终端号，确定设备
        EntityWrapper ldeviceEw = new EntityWrapper();
        ldeviceEw.eq("select_key", SelectKeyPack.getDeviceSelectKey(link.getShortAddr(),link.getEndPoint()));

        // 主动联动的设备，传感器什么的
        TblDevice linkDevice = deviceService.selectOne(ldeviceEw);
        List<TblDevice> deviceList = deviceService.selectBatchIds(ids);
        HashMap map = new HashMap();
        map.put("link",link);
        map.put("linkDevice",linkDevice);
        map.put("deviceList",deviceList);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,map));
    }
}
