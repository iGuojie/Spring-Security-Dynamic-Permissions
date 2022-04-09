package com.iguojie.rbac.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iguojie.rbac.config.exception.RequestModeException;
import com.iguojie.rbac.config.exception.VerifyCodeErrorException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyLoginFilter extends UsernamePasswordAuthenticationFilter {
    public static final String SPRING_SECURITY_FORM_KAPTCHA_KEY = "kaptcha";

    private String kaptchaParameter = SPRING_SECURITY_FORM_KAPTCHA_KEY;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //设置post
        if (!request.getMethod().equals("POST")) {
            throw new RequestModeException("Authentication method not supported: " + request.getMethod());
        }
        if (!request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            throw new RequestModeException("Content Type not supported: " + request.getContentType());
        }
        //json格式
        try {
            Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = userInfo.get(getUsernameParameter());
            String password = userInfo.get(getPasswordParameter());
            String kaptcha = userInfo.get(getKaptchaParameter());
            if(ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)){
                throw new UsernameNotFoundException("Parameter error");
            }
            String sessionVerifyCode = (String)request.getSession().getAttribute("kaptcha");
            if(ObjectUtils.isEmpty(sessionVerifyCode)){
                throw new VerifyCodeErrorException("验证码失效");
            }
            if(!sessionVerifyCode.equals(kaptcha)){
                throw new VerifyCodeErrorException("验证码不匹配");
            }
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException("登录失败");
    }

    public final String getKaptchaParameter() {
        return kaptchaParameter;
    }

    public final void setKaptchaParameter(String kaptchaParameter) {
        Assert.hasText(kaptchaParameter, "Username parameter must not be empty or null");
        this.kaptchaParameter = kaptchaParameter;
    }

}
