package com.aurora.gateway.common.model.auth;

import java.io.Serializable;
import java.util.List;

/**
 * 用户dto
 *
 * @author panda00hi
 * @date 2023.12.06
 */
public class UserDTO implements Serializable {

    private Integer id;

    /**
     * 用户账户
     */
    private String username;

    /**
     * 客户等级。0-超级管理员，1-租户，2-普通用户
     */
    private Integer level ;

    /**
     * 账号状态 1启用 0禁用
     */
    private Integer accStatus;

    /**
     * 限制放行。默认1-受限，0-不限
     * 新建用户默认为1，修改密码后，更新为0
     */
    private Integer limitPass = 1;

    /**
     * 客户id
     */
    private Integer cid;


    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 角色id
     */
    private List<Integer> roleIds;



    public UserDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(Integer accStatus) {
        this.accStatus = accStatus;
    }

    public Integer getLimitPass() {
        return limitPass;
    }

    public void setLimitPass(Integer limitPass) {
        this.limitPass = limitPass;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", level=" + level +
                ", accStatus=" + accStatus +
                ", limitPass=" + limitPass +
                ", cid=" + cid +
                ", tenantId=" + tenantId +
                ", roleIds=" + roleIds +
                '}';
    }
}
