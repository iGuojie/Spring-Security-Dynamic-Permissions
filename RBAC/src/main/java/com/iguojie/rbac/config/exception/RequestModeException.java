package com.iguojie.rbac.config.exception;


import org.springframework.security.core.AuthenticationException;

//请求方式错误
public class RequestModeException extends AuthenticationException {
    public RequestModeException(String msg) {
        super(msg);
    }
}
