package com.iguojie.rbac.config.exception;

import org.springframework.security.core.AuthenticationException;

//验证码错误
public class VerifyCodeErrorException extends AuthenticationException {
    public VerifyCodeErrorException(String msg) {
        super(msg);
    }
}