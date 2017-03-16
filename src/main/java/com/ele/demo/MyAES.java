package com.ele.demo;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * This program generates a AES key, retrieves its raw bytes, and then
 * reinstantiates a AES key from the key bytes. The reinstantiated key is used
 * to initialize a AES cipher for encryption and decryption.
 *
 * @author oukailiang
 * @create 2017-01-17 上午10:12
 */

public class MyAES {

    private final static Logger LOG = Logger.getLogger(MyAES.class);

    private static final String AES = "AES";
    //16个字符
    private static final String CRYPT_KEY = "#@REWQTasdfg*&^!";

    /**
     * 加密
     *
     * @param src
     * @param key
     * @return
     */
    private static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);//设置密钥和加密形式
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    private static String decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), AES);//设置加密Key
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);//设置密钥和解密形式
        return new String(cipher.doFinal(src));
    }

    /**
     * 二行制转十六进制字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        String hs = "", stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 十六机制转化为二进制
     *
     * @param b
     * @return
     */
    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 进行base64编码
     *
     * @param bytes
     * @return
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    /**
     * 进行base64解码
     *
     * @param str
     * @return
     */
    private static byte[] base64Decode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        try {
            if (data == null) {
                return null;
            }
            return base64Encode(encrypt(data.getBytes(), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(Long data) {
        try {
            if (data == null) {
                return null;
            }
            return base64Encode(encrypt(data.toString().getBytes(), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(Integer data) {
        try {
            if (data == null) {
                return null;
            }
            return base64Encode(encrypt(data.toString().getBytes(), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密string
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) {
        try {
            if (data == null) {
                return null;
            }
            return decrypt(base64Decode(data), CRYPT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密long
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static long decryptId(String data) {
        try {
            if (data == null) {
                return 0l;
            }
            return Long.parseLong(decrypt(base64Decode(data), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 解密int型加密后的字符串
     *
     * @param data
     * @return int数字
     */
    public static int decryptInt(String data) {
        try {
            if (data == null) {
                return 0;
            }
            return Integer.parseInt(decrypt(base64Decode(data), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        String ID = "123456789方法你好";
        String idEncrypt = encrypt(ID);
        System.out.println(idEncrypt);
        String idDecrypt = decrypt(idEncrypt);
        System.out.println(idDecrypt);
        //
        System.out.println("id en de ==============");
        Long idL = 11l;
        System.out.println("id" + idL.toString());
        String idLEncrypt = encrypt(idL);

        System.out.println(idLEncrypt);
        Long idLDecrypt = decryptId(idLEncrypt);
        System.out.println(idLDecrypt);
    }
}
