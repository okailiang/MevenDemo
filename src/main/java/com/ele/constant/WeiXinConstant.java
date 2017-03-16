package com.ele.constant;

/**
 * 微信公众号中使用到的常量
 *
 * @author oukailiang
 * @create 2016-09-18 下午6:18
 */

public class WeiXinConstant {

    public final static String APPID = "";
    public final static String SECTRET = "";
    public final static String AND_APPID = "&appid=" + APPID;
    public final static String AND_SECTRET = "&secret=" + SECTRET;
    public final static String AND_APPID_SECTRET = "&appid=" + APPID + "&secret=" + SECTRET;
    //全局ticket
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public final static String AUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    //auth2用户网页授权
    public final static String AUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    //刷新网页授权
    public final static String AUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    //jsapi_ticket
    public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    //统一下单接口提交
    public final static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //
    public final static String AUTH2_GET_USERIFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    //snsapi_base & snsapi_userinfo
    public final static String AUTH2_SNSAPI_BASE = "snsapi_base";
    public final static String AUTH2_SNSAPI_USERINFO = "snsapi_userinfo";
    //redirect_uri
    public final static String AUTH2_REDIRECT_URI = "http://4c1cf3c9.ngrok.io/weixin/getAuth.do";

    //start-微信付款配置信息
    //受理商ID，身份标识
    public static String MCHID = "";
    //商户支付密钥Key。审核通过后，在微信发送的邮件中查看
    public static String PAY_KEY = "";
    //重定向地址
    public static String REDIRECT_URL = "http://XXXXXXXXXXXXXXXXXXX/callWeiXinPay";
    //接收微信支付异步通知回调地址
    public static String NOTIFY_URL = "http://a4print.cn/A4print/weixin/test/";
    //web回调地址
    public static String WEB_NOTIFY_URL = "http://XXXXXXXXXXXXXXXXXXXXXXXXX/weixinPay_notify";
    //end-微信付款配置信息

    /**
     * 微信消息类型
     */
    public static class MsgType {

        /**
         * 事件消息
         */
        public final static String EVENT = "event";

        /**
         * 文本消息
         */
        public final static String TEXT = "text";

        /**
         * 图片消息
         */
        public final static String IMAGE = "image";

        /**
         * 声音消息
         */
        public final static String VOICE = "voice";

        /**
         * 视频消息
         */
        public final static String VIDEO = "video";

        /**
         * 小视频消息
         */
        public final static String SHORTVIDEO = "shortvideo";

        /**
         * 位置消息
         */
        public final static String LOCATION = "location";

        /**
         * 链接消息
         */
        public final static String LINK = "link";

        /**
         * 音乐消息
         */
        public final static String MUSIC = "music";

        /**
         * 图文消息
         */
        public final static String NEWS = "news";

        /**
         * 卡券消息
         */
        public final static String WXCARD = "wxcard";
    }

    /**
     * 微信消息的时间类型
     */
    public static class EventType {

        /**
         * 关注事件
         */
        public final static String SUBSCRIBE = "subscribe";

        /**
         * 取消关注事件
         */
        public final static String UNSUBSCRIBE = "unsubscribe";

        /**
         * 用户已关注时的事件推送
         */
        public final static String SCAN = "SCAN";

        /**
         * 上报地理位置事件
         */
        public final static String LOCATION = "LOCATION";

        /**
         * 点击菜单拉取消息时的事件推送
         */
        public final static String CLICK = "CLICK";

        /**
         * 点击菜单跳转链接时的事件推送
         */
        public final static String VIEW = "VIEW";
    }
}