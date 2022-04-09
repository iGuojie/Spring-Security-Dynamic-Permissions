package com.iguojie.rbac.bo;

public class RoleWithMenu {

    private String roleName;
    private String url;


    @Override
    public String toString() {
        return "RoleWithMenu{" +
                "roleName='" + roleName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
