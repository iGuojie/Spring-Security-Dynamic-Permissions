package com.iguojie.rbac.config.service;

import com.iguojie.rbac.dao.UserDao;
import com.iguojie.rbac.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(ObjectUtils.isEmpty(username)){
            throw new UsernameNotFoundException("用户名不能为空");
        }
        User user = userDao.selectByUsername(username);
        System.out.println(user);
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return user;
    }
}
