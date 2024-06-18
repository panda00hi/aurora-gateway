package com.aurora.gateway.common.model.req;


import java.io.Serializable;

/**
 * 通用分页查询请求对象
 */
public class BasePageReq implements Serializable {
    private Integer pageSize = 10;
    private Integer pageNum = 1;

    public BasePageReq() {
    }

    public BasePageReq(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}