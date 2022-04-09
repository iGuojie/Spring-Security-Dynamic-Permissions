package com.iguojie.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Menu implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 可以访问的url
     */
    private String url;


    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单描述
     *
     * @return description - 菜单描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置菜单描述
     *
     * @param description 菜单描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取可以访问的url
     *
     * @return url - 可以访问的url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置可以访问的url
     *
     * @param url 可以访问的url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}