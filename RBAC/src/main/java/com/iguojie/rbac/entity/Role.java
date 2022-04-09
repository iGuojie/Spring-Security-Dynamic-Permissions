package com.iguojie.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Role implements Serializable {
    /**
     * 角色id
     */
    @Id
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色中文名
     */
    @Column(name = "name_zh")
    private String nameZh;


    /**
     * 获取角色id
     *
     * @return id - 角色id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色id
     *
     * @param id 角色id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色名
     *
     * @return name - 角色名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色中文名
     *
     * @return name_zh - 角色中文名
     */
    public String getNameZh() {
        return nameZh;
    }

    /**
     * 设置角色中文名
     *
     * @param nameZh 角色中文名
     */
    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameZh='" + nameZh + '\'' +
                '}';
    }
}