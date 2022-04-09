package com.iguojie.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "role_menu")
public class RoleMenu implements Serializable {
    /**
     * menu的主键
     */
    @Id
    private Integer mid;

    /**
     * role的主键
     */
    private Integer rid;

    /**
     * 获取menu的主键
     *
     * @return mid - menu的主键
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * 设置menu的主键
     *
     * @param mid menu的主键
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * 获取role的主键
     *
     * @return rid - role的主键
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * 设置role的主键
     *
     * @param rid role的主键
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }
}