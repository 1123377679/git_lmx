package org.lanqiao.config;

import com.baomidou.mybatisplus.extension.plugins.*;
import org.springframework.context.annotation.*;

@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
