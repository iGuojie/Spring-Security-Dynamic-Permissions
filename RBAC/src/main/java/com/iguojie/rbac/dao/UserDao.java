package com.iguojie.rbac.dao;

import com.iguojie.rbac.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {
    User selectByUsername(@Param("username")String username);
}