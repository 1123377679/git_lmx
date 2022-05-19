package org.lanqiao.controller;

import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.core.metadata.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import org.lanqiao.pojo.*;
import org.lanqiao.service.*;
import org.lanqiao.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MovieController {
    @Autowired
    private MovieService service;

    /**
     * 分页查询功能
     * @param model
     * @return
     */
    @RequestMapping("/movie_list")
    public String list(@RequestParam(defaultValue = "") String title,
                       @RequestParam(defaultValue = "1") Long pageIndex,
                       @RequestParam(defaultValue = "10") Long pageSize,
                       Model model){
        //查询所有没有删除的数据
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        //查询数据库字段名del = 1的数据
        queryWrapper.eq("del",1);
        if (!"" .equals(title)){
            queryWrapper.like("title",title);
        }
        //使用mybatis-plus的分页
        IPage<Movie> page = service.page(new Page<>(pageIndex, pageSize), queryWrapper);
        //封装工具类
        PageHelper<Movie> pageHelper = new PageHelper<Movie>(pageIndex,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        //将所有分页数据存入model中
        model.addAttribute("pageHelper",pageHelper);
        model.addAttribute("title",title);
        return "list";
    }
}
