package com.iguojie.rbac.dao;

import com.iguojie.rbac.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
@Repository
public interface MenuDao extends BaseMapper<Menu> {
}