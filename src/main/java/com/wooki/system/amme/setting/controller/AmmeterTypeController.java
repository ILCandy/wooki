package com.wooki.system.amme.setting.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.amme.setting.service.AmmeterTypeService;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-02-04
 */
@RestController
@RequestMapping("/ammeterType")
public class AmmeterTypeController {

    @Autowired
    AmmeterTypeService ammeterTypeService;

    /**
     * 电表类型列表
     * @return
     */
    @GetMapping("/list")
    public Object typeList(){
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,ammeterTypeService.selectList(new EntityWrapper<>())));
    }
}
