package com.wooki.system.tbl.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/12
 * Time : 下午4:04
 */
@Controller
@RequestMapping("/admin")
public class Admin {
    @RequestMapping("/login.html")
    public String login(){
        return "/login";
    }
    @RequestMapping("/index.html")
    public String index(){
        return "/admin/index";
    }
    @RequestMapping("temp/{a}.html")
    public String tempHtml(@PathVariable("a")String a){
//    public String tempHtml(){
//        return "/admin/temp/"+uri;
        return "/admin/temp/"+a;
    }

    @RequestMapping("/aa")
    public String home(){
        return "/admin/login";
    }
}
