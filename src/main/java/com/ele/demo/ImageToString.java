package com.ele.demo;

import java.io.*;
import java.util.Base64;

/**
 * 图片和字符串之间转换
 *
 * @author oukailiang
 * @create 2017-03-16 下午1:56
 */

public class ImageToString {

    public static String imageToString(String filePath) {
        File file = new File(filePath);
        return imageToString(file);
    }

    public static String imageToString(File file) {
        String imageStr;
        byte[] bytes = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        imageStr = byteToHex(bytes);
        //base64编码
        // imageStr = Base64.getEncoder().encodeToString(bytes);
        return imageStr;
    }

    public static void stringToImage(String imageStr, String outPath) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(outPath);
            //base64解码
            //byte[] imageByte = Base64.getDecoder().decode(imageStr);
            byte[] imageByte = hexToByte(imageStr);
            InputStream in = new ByteArrayInputStream(imageByte);
            byte[] temp = new byte[2048];
            int readLen;
            while ((readLen = in.read(temp)) != -1) {
                fos.write(temp, 0, readLen);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 二进制转换为十六进制
     *
     * @param bytes
     * @return
     */
    private static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String hex;
        for (int n = 0; n < bytes.length; n++) {
            hex = Integer.toHexString(bytes[n] & 0XFF);
            if (hex.length() == 1) {
                sb.append("0" + hex);
            } else {
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    /**
     * 十六进制转化为二进制
     *
     * @param str
     * @return
     */
    private static byte[] hexToByte(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1) {
            return null;
        }

        byte[] bytes = new byte[len / 2];
        for (int n = 0; n < len; n += 2) {
            bytes[n / 2] = (byte) Integer.decode("0x" + str.substring(n, n + 2)).intValue();
        }
        return bytes;
    }

    public static void main(String[] args) {
        String imageStr = imageToString("/Users/oukailiang/Desktop/750.jpg");
        System.out.println(imageStr);
        System.out.println(imageStr.length());
        stringToImage(imageStr, "/Users/oukailiang/Desktop/7501.jpg");
    }
}
