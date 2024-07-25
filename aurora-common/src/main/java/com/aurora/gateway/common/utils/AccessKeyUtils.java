package com.aurora.gateway.common.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * 接入key和secret工具类
 *
 * @author panda00hi
 * @date 2024.07.10
 */
public class AccessKeyUtils {

    /**
     * 生成Access Key
     *
     * @return 默认32位UUID
     */
    public static String generateAccessKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成Secret Key
     *
     * @return 默认32位
     */
    public static String generateSecretKey() {
        return generateSecretKey(32);
    }

    public static String generateSecretKey(int length) {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            // 如果没有强安全算法可用，回退到默认的SecureRandom实例
            secureRandom = new SecureRandom();
        }
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("ak=" + generateAccessKey());
        System.out.println("sk=" + generateSecretKey());
        System.out.println("sk=" + generateSecretKey(10));
        System.out.println("========================================");

    }

}
