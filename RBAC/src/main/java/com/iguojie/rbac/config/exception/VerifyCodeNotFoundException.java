package com.iguojie.rbac.config.exception;

import org.springframework.security.core.AuthenticationException;

//没有发现验证码异常类
public class VerifyCodeNotFoundException extends AuthenticationException {
    public VerifyCodeNotFoundException(String msg) {
        super(msg);
    }
}
