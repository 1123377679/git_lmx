package org.lanqiao.controller;

import cn.hutool.core.bean.*;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.*;
import org.lanqiao.pojo.*;
import org.lanqiao.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;
import java.util.concurrent.*;

@Controller
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    StringRedisTemplate redisTemplate;

    //登录功能逻辑以及往redis里面存储缓存
    @PostMapping("/login")
    public String userLogin(User user, HttpSession session, Model model,String userCode) {
        //从session中获取验证码的信息
        String sysCode = (String) session.getAttribute("sysCode");
        if (userCode != null && userCode.equalsIgnoreCase(sysCode)){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userName", user.getUserName());
            queryWrapper.eq("password", user.getPassword());
            queryWrapper.eq("state", "0");
            //获取的数据库用户名和密码和状态码
            User one = service.getOne(queryWrapper);
            if (one != null) {
                if (one.getState().equals("0") ) {
                    session.setAttribute("admin", one);
                    System.out.println("登录成功" + one);
                    //第一步：随机生成token，作为登录令牌
                    String token = UUID.randomUUID().toString(true);
                    //第二步，将user对象转为hash进行存储
                    Map<String, Object> map = BeanUtil.beanToMap(one);
                    //第三步：将验证码和用户信息往redis里面进行存储
                    redisTemplate.opsForValue().set("login:token:code:"+token,sysCode);
                    redisTemplate.opsForHash().putAll("login:token:"+token,map);
                    //第四步:设置token有效期
                    redisTemplate.expire("login:token:"+token,30L, TimeUnit.MINUTES);
                    redisTemplate.expire("login:token:code:"+token,30L, TimeUnit.MINUTES);
                    return "index";
                } else {
                    model.addAttribute("message", "该账户被禁用");
                    return "login";
                }
            }else{
                System.out.println("登录失败");
                model.addAttribute("message", "用户名或密码错误");
                return "login";
            }
        }else {
            System.out.println("验证码错误");
            model.addAttribute("message","验证码错误");
            return "login";
        }
    }

    //注册功能
    @PostMapping("/register")
    public String reGister(User user, Model model){
        user.setState("0");
        //将数据都存入数据库当中
        boolean save = service.save(user);
        //如果成功true跳转登录页面
        if (save){
            return "login";
        }else {
            //如果失败false跳转注册页面
            return "register";
        }
    }
}
