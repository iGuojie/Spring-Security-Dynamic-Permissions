package com.iguojie.rbac.dao;

import com.iguojie.rbac.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
@Repository
public interface RoleMenuDao extends BaseMapper<RoleMenu> {
}