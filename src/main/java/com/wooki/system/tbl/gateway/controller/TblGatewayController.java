package com.wooki.system.tbl.gateway.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.function.annotation.Admin;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.common.GatewayClient;
import com.wooki.system.tbl.gateway.dto.GatewayRefreshDto;
import com.wooki.system.tbl.gateway.service.TblGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@RestController
@RequestMapping("/gateway")
public class TblGatewayController {

    @Autowired
    TblGatewayService gatewayService;

    /**
     * 刷新网关的设备列表
     * @param refreshDto gatewayId 网关id
     * @return
     * @throws IOException
     */
    @Admin
    @PostMapping("/refreshDevice")
    public Object refreshDevice(GatewayRefreshDto refreshDto)throws IOException,InterruptedException{
        GatewayClient.send(refreshDto.getGatewayId(), Constant.GET_DEVICE_LIST);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 网关列表
     * @param page ，分页参数
     * @return
     */
    @Admin
    @GetMapping("/list")
    public Object gatewayList(Page page){
        page = gatewayService.selectPage(page);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, page));
    }

    /**
     * b用户网关列表
     * @param request
     * @return
     */
//    @BUser
//    @GetMapping("bUserList")
//    public Object bUserGatewayList(HttpServletRequest request,Page page){
//        EntityWrapper gatewayEW = new EntityWrapper();
//        gatewayEW
//                .eq("")
//    }
}
