package com.caigen.graduationproject.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 16:56
 * @description Druid数据库连接池配置
 */
@Configuration
public class DruidConfig {

    /**
     * 告诉方法druid，你给我返回一个DruidDataSoruc类型的bean
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /**
     * @return 配置Druid的监控
     * @Bean   配置一个管理后台的Servlet
     */
    @Bean
    public ServletRegistrationBean stateViewServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParam = new HashMap<>();
        initParam.put("loginUsername","druid");
        initParam.put("loginPassword","druid");
        //不写，默认允许所有
        initParam.put("allow","");
        registrationBean.setInitParameters(initParam);
        return registrationBean;

    }
    /**
     * 2、配置一个监控的filter
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParam = new HashMap<>();
        initParam.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParam);

        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
