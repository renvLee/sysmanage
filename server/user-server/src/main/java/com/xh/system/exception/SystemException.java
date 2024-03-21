package com.xh.system.exception;


import com.xh.security.enums.HttpResponseEnum;

//统一异常处理
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    //定义一个构造方法，接收的参数是枚举类型，AppHttpCodeEnum是我们在lengde-framework工程定义的枚举类
    public SystemException(HttpResponseEnum httpCodeEnum) {
        super(httpCodeEnum.getMessage());
        //把某个枚举类里面的code和msg赋值给异常对象
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMessage();
    }
}
