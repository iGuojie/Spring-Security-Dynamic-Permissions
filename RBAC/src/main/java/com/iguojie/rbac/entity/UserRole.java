package com.iguojie.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "user_role")
public class UserRole implements Serializable {
    /**
     * user主键
     */
    private Integer uid;

    /**
     * role主键
     */
    private Integer rid;

    /**
     * 获取user主键
     *
     * @return uid - user主键
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置user主键
     *
     * @param uid user主键
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取role主键
     *
     * @return rid - role主键
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * 设置role主键
     *
     * @param rid role主键
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }
}