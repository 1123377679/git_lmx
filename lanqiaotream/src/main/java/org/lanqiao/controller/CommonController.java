package org.lanqiao.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

//公共控制层
@Controller
public class CommonController {
    /**
     * 跳转login页面
     */
    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }
    /**
     * 跳转注册页面
     *
     */
    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }
}
