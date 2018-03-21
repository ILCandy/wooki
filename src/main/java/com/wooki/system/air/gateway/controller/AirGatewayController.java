package com.wooki.system.air.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.wooki.system.air.gateway.dto.AirGatwayDto;
import com.wooki.system.air.gateway.dto.IdAirGatwayDto;
import com.wooki.system.air.gateway.entity.AirGateway;
import com.wooki.system.air.gateway.service.AirGatewayService;
import com.wooki.system.air.gateway.service.CompanyService;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.air.gateway.controller
 * @date 2018/3/19
 */
@RestController
@RequestMapping("/aircon")
public class AirGatewayController {

    @Resource
    public AirGatewayService gatewayService;

    @Resource
    public CompanyService companyService;

//    @RequestMapping("/gatewayList")
//    public Iterable<AirGateway> getAll(){
//        return gatewayService.getAll();
//    }

    @GetMapping("/gatewayList")
    public Object getAirGateway(){
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, gatewayService.getAll()));
    }

    @GetMapping("/refreshGatewayList")
    public Object refreshAirGateway(){
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, gatewayService.getAll()));
    }

    @PostMapping("/bind")
    public Object bindCompany(@RequestBody IdAirGatwayDto airGatwayDto){
        String name = companyService.getOne(airGatwayDto.getCompanyId()).getName();
        AirGateway airGateway = gatewayService.findByUid(airGatwayDto.getId());
        airGateway.setAllot_company(name);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    @PostMapping("/update")
    public Object updateGatewayByUid(@RequestBody AirGatwayDto gatwayDto){
        AirGateway airGateway = gatewayService.findByUid(gatwayDto.getId());
        airGateway.setGateway_name(gatwayDto.getName());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

}
