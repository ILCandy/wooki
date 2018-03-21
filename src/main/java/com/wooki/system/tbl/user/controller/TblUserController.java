package com.wooki.system.tbl.user.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@RestController
@RequestMapping("/user")
public class TblUserController {

    @PostMapping("userLogin")
    public ModelAndView userLogin(HttpServletRequest request){
        return new ModelAndView("redirect:/index");
    }
}
