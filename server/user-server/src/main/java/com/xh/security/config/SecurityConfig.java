package com.xh.security.config;

import com.xh.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
//实现Security提供的WebSecurityConfigurerAdapter类，就可以改变密码校验的规则了
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    //注入我们在filter目录写好的类
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    //注入Security提供的认证失败的处理器，这个处理器里面的AuthenticationEntryPointImpl实现类，用的不是官方的了，
    //而是用的是我们在handler目录写好的AuthenticationEntryPointImpl实现类，因为我们也是添加到容器把官方的这个实现类覆盖了
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    //注入Security提供的授权失败的处理器，这个处理器里面的AccessDeniedHandlerImpl实现类，用的不是官方的了，
    //而是用的是我们在handler目录写好的AccessDeniedHandlerImpl实现类，因为我们也是添加到容器把官方的这个实现类覆盖了
    private AccessDeniedHandler accessDeniedHandler;
    @Bean
    //把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验
    //注意也可以注入PasswordEncoder，效果是一样的，因为PasswordEncoder是BCry..的父类
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //由于是前后端分离项目，所以要关闭csrf
                .csrf().disable()
                //由于是前后端分离项目，所以session是失效的，我们就不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //指定让spring security放行登录接口的规则
                .authorizeRequests()
                //无论登录了还是没有登录，都可允许访问
                .antMatchers("/hello/per").permitAll()
                // 对于登录接口 anonymous表示允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 除上面外的所有请求全部需要鉴权认证后，才能访问
                .anyRequest().authenticated();
        /*认证过滤器的实现*/
        //把token校验过滤器添加到过滤器链中
        //第一个参数是上面注入的我们在filter目录写好的类，第二个参数表示你想添加到哪个过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        /*异常处理的相关配置*/
        http.exceptionHandling()
                //配置认证失败的处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                //配置授权失败的处理器
                .accessDeniedHandler(accessDeniedHandler);
        /*允许跨域*/
        http.cors();
    }
}