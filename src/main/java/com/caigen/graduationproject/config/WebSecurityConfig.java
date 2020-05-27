package com.caigen.graduationproject.config;

import ch.ethz.ssh2.auth.AuthenticationManager;
import com.caigen.graduationproject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.Authenticator;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 16:52
 * @description springsecurity配置项
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf保护，方便后面有时间再开启配置
        http.csrf().disable();

        //指定页面所有人都可以访问，其他页面，必须登录才能访问
        http.authorizeRequests()
                .antMatchers("/","/login","/about","/index")
                .permitAll()
                .and()
            .authorizeRequests()
                .anyRequest()
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
        //登录页的请求配置
        http.authorizeRequests().and().formLogin()//
                // 登录页的表单提交地址
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/main")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                // 配置登出路径
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");

        //开启 remember me功能
        http.rememberMe().tokenValiditySeconds(3600);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**","/images/**","customjs/**","/fonts/**");
    }
}
