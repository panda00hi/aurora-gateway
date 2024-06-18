package com.aurora.gateway.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panda00hi
 * @date 2024.04.17
 */
public class SignUtils {


    public static void main(String[] args) {
        String accessKey = "akXXX";
        String requestTime = String.valueOf(System.currentTimeMillis());
        String sk = "skXXX";
        String uri = "/test/runData";
        String nonce = "abcdef";

        JSONObject dataJson = new JSONObject();
        dataJson.put("sn", "123123");
        dataJson.put("measurement", "arr");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("data", dataJson);
        String sign = generateSign(accessKey, requestTime, uri, nonce, sk, JSON.toJSONString(bodyMap));
        System.out.println(sign);
    }


    public static String generateSign(String accessKey, String requestTime,
                                      String uri, String nonce, String sk, String params) {
        StringBuffer stringBuffer = new StringBuffer(1024);
        stringBuffer.append(accessKey);
        stringBuffer.append("|");
        stringBuffer.append(requestTime);
        stringBuffer.append("|");
        stringBuffer.append(uri);
        stringBuffer.append("|");
        stringBuffer.append(nonce);

        // 第一步加密
        HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, stringBuffer.toString());
        byte[] kDate = hmac.hmac(sk);
        // 原始基数
        byte[] messageType = DigestUtils.sha256(params);
        // 加密后的sk再对数据源做一层加密
        hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, kDate);
        return hmac.hmacHex(messageType);
    }
}
