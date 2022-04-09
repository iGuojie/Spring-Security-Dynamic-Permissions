package com.iguojie.rbac.config;

import com.iguojie.rbac.config.filter.MyFilterInvocationSecurityMetadataSource;
import com.iguojie.rbac.config.filter.MyFilterSecurityInterceptor;
import com.iguojie.rbac.config.filter.MyLoginFilter;
import com.iguojie.rbac.config.filter.RoleAccessDecisionManager;
import com.iguojie.rbac.config.handler.*;
import com.iguojie.rbac.config.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //获取用户账号密码及权限信息
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    //登录成功处理
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    //登录失败处理
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    //认证失败处理
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    //权限不足处理器
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    //自定义访问鉴权空控制器
    @Autowired
    private MyFilterSecurityInterceptor MySecurityInterceptor;

    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Autowired
    private RoleAccessDecisionManager roleAccessDecisionManager;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //登录验证过滤器
    @Bean
    public MyLoginFilter myLoginFilter() throws Exception {
        MyLoginFilter loginFilter = new MyLoginFilter();
        loginFilter.setFilterProcessesUrl("/login.do");
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        loginFilter.setKaptchaParameter("kaptcha");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域检测以及跨站请求伪造检测
        http.cors().and().csrf().disable();
        http.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(roleAccessDecisionManager);
                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                }).
                and().
                authorizeHttpRequests().
                antMatchers("/verificationCode.do").
                permitAll().
                and().
                formLogin().permitAll().
                and().
                logout().
                logoutSuccessHandler(myLogoutSuccessHandler).
                deleteCookies().
                clearAuthentication(true).
                and().
                exceptionHandling().
                authenticationEntryPoint(myAuthenticationEntryPoint).   //认证失败
                accessDeniedHandler(myAccessDeniedHandler);
        http.addFilterAt(myLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(MySecurityInterceptor, FilterSecurityInterceptor.class);
    }
}
