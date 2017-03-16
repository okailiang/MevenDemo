package com.ele;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ele.model.weixin.BaseEvent;
import com.ele.model.weixin.LocationMessage;
import com.ele.util.CollectionUtls;
import com.ele.util.HttpUtils;
import com.ele.util.WeiXinMsgUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * 微信公众号测试
 *
 * @author oukailiang
 * @create 2016-09-18 下午3:46
 */

public class WeiXinTest {
    private static String APPID = "wx44b72fe509e9f852";
    private static String SECTRET = "0d0729f805e0803e3959b9554b4989dc";
    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 获得全局access_token
     *
     * @return
     */
    public static String getAccessToken() {
        String param = "grant_type=client_credential" + "&appid=" + APPID + "&secret=" + SECTRET;
        String result = HttpUtils.sendGet(ACCESS_TOKEN_URL, param);
        JSONObject jsonObject = JSON.parseObject(result);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    public static void main(String[] args) throws Exception {
        //getAccessToken();
        String str = "<xml>\n" +
                "<toUserName>uuu</toUserName>\n" +
                "<FromUserName>rrr</FromUserName>\n" +
                "<CreateTime>123456789</CreateTime>\n" +
                "<MsgType>rrrr</MsgType>\n" +
                "<Location_X>23.134521</Location_X>\n" +
                "<Location_Y>113.358803</Location_Y>\n" +
                "<Scale>20</Scale>\n" +
                "<label><![CDATA[位置信息]]></label>\n" +
                "<MsgId>1234567890123456</MsgId>" +
                "</xml>";
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        Map map = WeiXinMsgUtils.parseXml(inputStream);
        System.out.println("map=" + map.toString());
        // BaseEvent baseEvent = (BaseEvent) CollectionUtls.mapToObject1(map, BaseEvent.class);
        //BeanUtils.copyProperties(map,baseEvent);
        LocationMessage baseEvent = (LocationMessage) WeiXinMsgUtils.mapToObject(map, LocationMessage.class);
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis() / 1000);
        String xml = WeiXinMsgUtils.toXml(baseEvent);
        System.out.println("xml=\n" + xml);


    }
}
