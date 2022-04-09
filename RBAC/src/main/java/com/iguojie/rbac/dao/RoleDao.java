package com.iguojie.rbac.dao;

import com.iguojie.rbac.bo.RoleWithMenu;
import com.iguojie.rbac.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RoleDao extends BaseMapper<Role> {

    List<RoleWithMenu> queryRoleWithMenu();
}