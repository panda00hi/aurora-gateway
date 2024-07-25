package com.aurora.dubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户dto
 *
 * @author panda00hi
 * @date 2023.12.06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;

    /**
     * 用户账户
     */
    private String username;

    /**
     * 客户等级。0-超级管理员，1-租户，2-普通用户
     */
    private Integer level;

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

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 角色id
     */
    private List<Integer> roleIds;

}
