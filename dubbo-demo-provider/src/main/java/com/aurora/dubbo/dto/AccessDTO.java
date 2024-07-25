package com.aurora.dubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户接入DTO
 * 包含 ak sk，以及有效期的计量
 *
 * @author panda00hi
 * @date 2023.12.06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessDTO implements Serializable {


    private Integer id;

    private String tenantName;

    private String accessKey;

    private String secretKey;

    // 状态。0-正常，1-禁用
    private int status;

    // 到期时间戳。当前时间超过该字段时，即视为已过期
    private long expireTime;
}
