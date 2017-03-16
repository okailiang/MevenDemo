package com.ele.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author oukailiang
 * @create 2016-10-09 上午10:32
 */

public class WeiXinUtils {
    /**
     * 解析微信发来的xml消息
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static Map parseXml(InputStream inputStream)
            throws Exception {
        // 将解析结果存储在HashMap中
        Map map = new HashMap();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();

        return map;
    }

    /**
     * 获取随机数
     *
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return DigestUtils.md5Hex(String.valueOf(random.nextInt(10000)));
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 获得统一下单签名
     *
     * @param paramMap
     * @param md5key
     * @return
     */
    public static String getMd5Sign(Map<String, Object> paramMap, String md5key) {
        StringBuffer sb = new StringBuffer();
        List<String> keyList = new ArrayList<String>(paramMap.keySet());
        //排序
        Collections.sort(keyList);
        int size = keyList.size();
        for (int var = 0; var < size; var++) {
            String key = keyList.get(var);
            String value = String.valueOf(paramMap.get(key));
            if ("null".equals(value) || "sign".equals(value)) {
                continue;
            }
            sb.append(key).append("=").append(value).append("&");
        }
        sb.append("key=").append(md5key);

        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }


    /**
     * 创建签名SHA1
     */
    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        System.out.println("sha1 sb:" + params);
        return getSha1(params);
    }

    //Sha1签名
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("utf-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将对象转换为xml
     *
     * @param object
     * @return
     */
    public static String toXml(Object object) {
        xstream.alias("xml", object.getClass());
        return xstream.toXML(object);
    }

    /**
     * 将Map转为xml
     *
     * @param paramMap
     * @return
     */
    public static String toXml(Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>\n");
        for (String key : paramMap.keySet()) {
            String value = String.valueOf(paramMap.get(key));
            if ("null".equals(value)) {
                continue;
            }
            sb.append("<" + key + ">").append(value).append("</" + key + ">\n");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = false;

                public void startNode(String name, Class clazz) {
                    cdata = false;
                    if ("detail".equals(name)) {
                        cdata = true;
                    }
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
