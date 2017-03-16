package com.ele.enums;

/**
 * 微信使用到枚举
 *
 * @author oukailiang
 * @create 2016-09-23 下午7:59
 */

public class WeiXinEnum {
    /**
     * 微信事件类型
     */
    public enum MsgType {

        event, text, image, voice, video, shortvideo, link, music, news, wxcard;

        public static MsgType getType(String type) {
            return valueOf(type);
        }
    }

    /**
     * 微信事件消息类型
     */
    public enum EventType {
        subscribe, unsubscribe, SCAN, LOCATION, CLICK, VIEW;

        public static EventType getType(String type) {
            return valueOf(type);
        }
    }

}
