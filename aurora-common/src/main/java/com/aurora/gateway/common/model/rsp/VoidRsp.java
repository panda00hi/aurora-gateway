package com.aurora.gateway.common.model.rsp;


import com.aurora.gateway.common.model.base.BaseErrorCodeClass;

public class VoidRsp extends CommonResult<Void> {
    public VoidRsp() {
    }

    public VoidRsp(BaseErrorCodeClass errorCode) {
        super(errorCode);
    }

    public VoidRsp(Integer code, String msg) {
        super(code, msg, null);
    }
}