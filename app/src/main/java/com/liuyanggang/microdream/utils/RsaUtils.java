package com.liuyanggang.microdream.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.PRIVATE_KEY;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.PUBLIC_KEY;

/**
 * @ClassName RsaUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/20
 * @Version 1.0
 */
public class RsaUtils {
    /**
     * 解密
     *
     * @param text
     * @return
     */
    public static String mDecrypt(String text) {
        // 密码解密
        RSA rsa = new RSA(PRIVATE_KEY, null);
        return new String(rsa.decrypt(text, KeyType.PrivateKey));
    }

    /**
     * 加密
     *
     * @param text
     * @return
     */
    public static String mEncrypt(String text) {
        // 密码加密
        RSA rsa = new RSA(null, PUBLIC_KEY);
        return new String(rsa.encryptBase64(text, KeyType.PublicKey));
    }
}
