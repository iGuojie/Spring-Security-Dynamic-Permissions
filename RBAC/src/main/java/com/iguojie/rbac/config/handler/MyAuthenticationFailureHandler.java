package com.iguojie.rbac.config.handler;

import com.alibaba.fastjson.JSON;
import com.iguojie.rbac.common.entity.JsonResult;
import com.iguojie.rbac.common.enums.ResultCode;
import com.iguojie.rbac.config.exception.RequestModeException;
import com.iguojie.rbac.config.exception.VerifyCodeErrorException;
import com.iguojie.rbac.config.exception.VerifyCodeNotFoundException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        JsonResult result = null;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = JsonResult.fail(ResultCode.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof UsernameNotFoundException) {
            //密码错误
            result = JsonResult.fail(ResultCode.USER_ACCOUNT_OR_PASSWORD_ERROR);
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result = JsonResult.fail(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = JsonResult.fail(ResultCode.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = JsonResult.fail(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            //账号锁定
            result = JsonResult.fail(ResultCode.USER_ACCOUNT_LOCKED);
        } else if(e instanceof RequestModeException) {
            //请求方式错误
            result = JsonResult.fail(ResultCode.METHOD_ERROR);
        } else if(e instanceof VerifyCodeNotFoundException) {
            //没有验证码
            result = JsonResult.fail(ResultCode.VERIFY_CODD_NOT_FOUND);
        } else if(e instanceof VerifyCodeErrorException) {
            //验证码错误
            result = JsonResult.fail(ResultCode.VERIFY_CODD_ERROR);
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = JsonResult.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }else{
            //其他错误
            result = JsonResult.fail(e.getMessage());
        }
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
