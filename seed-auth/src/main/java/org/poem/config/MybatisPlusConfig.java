package org.poem.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {

        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        //格式化sql语句
        Properties properties =new Properties();
        properties.setProperty("format", "false");
        sqlExplainInterceptor.setProperties(properties);
        return sqlExplainInterceptor;
    }

}