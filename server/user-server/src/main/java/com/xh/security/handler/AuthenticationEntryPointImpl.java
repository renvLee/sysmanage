package com.xh.security.handler;

import com.alibaba.fastjson.JSON;
import com.xh.security.enums.HttpResponseEnum;
import com.xh.security.result.ResponseResult;
import com.xh.security.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//这个类只处理认证异常，不处理授权异常
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    //第一个参数是请求对象，第二个参数是响应对象，第三个参数是异常对象。把异常封装成授权的对象，然后封装到handle方法
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //ResponseResult是我们在domain目录写好的实体类。HttpStatus是spring提供的枚举类，UNAUTHORIZED表示401状态码
        ResponseResult result =  ResponseResult.fail(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重新登录，再试一下");
        //把上面那行拿到的result对象转换为JSON字符串
        String json = JSON.toJSONString(result);
        //WebUtils是我们在utils目录写好的类
        WebUtils.renderString(response,json);
    }
}