package com.ele.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ele.constant.WeiXinConstant;
import com.ele.enums.WeiXinEnum;
import com.ele.model.weixin.*;
import com.ele.util.CollectionUtls;
import com.ele.util.HttpUtils;
import com.ele.util.WeiXinMsgUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * 微信相关请求
 *
 * @author oukailiang
 * @create 2016-09-13 下午6:42
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeiXinAction {

    private static Logger Log = Logger.getLogger(WeiXinAction.class);


    /**
     * 测试微信公众号配置的url,用户关注存取用户信息,取关删除用户信息，点击菜单校验用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAuth.do")
    public String getAuth(HttpServletRequest request,
                          HttpServletResponse response) {
        AuthAccessToken authAccessToken = null;
        String openId = null;
        String redirectUrl = request.getParameter("redirectUrl");
        Log.info("redirectUrl=" + redirectUrl);
        HttpSession session = request.getSession(true);

        session.setAttribute("okl", "getAuthoukailiang");
        Log.info("getAuth sessionId=" + session.getId());
        Object obj = session.getAttribute("openId");
        Log.info(obj);
        openId = (String) session.getAttribute("openId");
        authAccessToken = (AuthAccessToken) session.getAttribute("authAccessToken");

        //1.获得code
        String code = request.getParameter("code");
        Log.info("code=" + code);
        if (openId == null) {

            //2.换区access_token
            authAccessToken = getAuthAccessToken(code);
            openId = authAccessToken.getOpenid();
            //3.刷新access_token（如果需要）
            //https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
            //4.
            //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            if (openId == null) {
                //请求数据出错
                return "redirect:/returnCodeMsg.do" + "?errcode=" + authAccessToken.getErrcode()
                        + "&errmsg=" + authAccessToken.getErrmsg();
            }
            session.setAttribute("authAccessToken", authAccessToken);
            session.setAttribute("openId", openId);
        }
        return "redirect:/" + redirectUrl + "?openId=" + openId;
    }

    /**
     * 测试微信公众号配置的url,用户关注存取用户信息,取关删除用户信息，点击菜单校验用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getOpenId.do")
    @ResponseBody
    public void getOpenId(HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            Enumeration<String> pNmaes = request.getParameterNames();
            String qStr = request.getQueryString();
            Map map = request.getParameterMap();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Log.info("reward url getOpenId sessionId=" + session.getId());
                String okl = (String) session.getAttribute("okl");
            }
            getAuthCode();
            //WeiXinMsgUtils.parseXml(request.getInputStream());
            response.getWriter().write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAuthCode() {
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx44b72fe509e9f852&redirect_uri=http://4c1cf3c9.ngrok.io/weixin/getAuth.do&response_type=code&scope=snsapi_base&state=1#wechat_redirect
        StringBuffer sb = new StringBuffer();
        sb.append(WeiXinConstant.AUTH2_AUTHORIZE_URL);
        sb.append("?appid=" + WeiXinConstant.APPID);
        sb.append("&redirect_uri=" + WeiXinConstant.AUTH2_REDIRECT_URI);
        //snsapi_base & snsapi_userinfo
        sb.append("&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        String result = HttpUtils.sendGet(sb.toString());
        Log.info("getAuthCode result=" + result);
    }

    /**
     * 测试微信公众号配置的url,用户关注存取用户信息,取关删除用户信息，点击菜单校验用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/access.do")
    @ResponseBody
    public void weiXinAccess(HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            String method = request.getMethod().toLowerCase();
            Log.info("request method=" + method);
            //get方法，一般用于微信服务器与本机服务器开发的基本配置（就是token验证，确定服务器是你的）
            if ("get".equals(method)) {
                response.getWriter().write(isConfigUrl(request, response));
                //post方法，一般是用户的事件处理（例如：关注/取消关注、点击按钮、发送消息....）
            } else if ("post".equals(method)) {
                HttpSession session = request.getSession(true);
                Log.info("access message sessionId=" + session.getId());
                session.setAttribute("okl", "oukailiang");
                response.getWriter().write(handleWeiXinMessage(request));
            }

        } catch (Exception e) {
            Log.error("test weixin access fail:" + e.getMessage() + "\n");
        }
    }

    /**
     * 微信返回报错时处理
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "returnCodeMsg.do", method = RequestMethod.GET)
    public String returnCodeMsg(HttpServletRequest request, ModelMap model) {
        model.addAttribute("errcode", request.getParameter("errcode"));
        model.addAttribute("errMsg", request.getParameter("errMsg"));
        return "error";
    }

    /**
     * 获得全局token
     *
     * @return 返回null获得全局token失败
     */
    public String getGlobalToken() {
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        StringBuffer sb = new StringBuffer();
        sb.append(WeiXinConstant.ACCESS_TOKEN_URL);
        sb.append("?grant_type=client_credential");
        sb.append(WeiXinConstant.AND_APPID_SECTRET);
        String result = HttpUtils.sendGet(sb.toString());
        GlobalToken globalToken = JSON.parseObject(result, GlobalToken.class);
        return globalToken.getAccess_token();
    }

    /**
     * 获取getAuthAccessToken
     *
     * @param code 获取用户信息微信授权码
     * @return
     */
    private AuthAccessToken getAuthAccessToken(String code) {
        AuthAccessToken authAccessToken = new AuthAccessToken();
        StringBuffer sb = new StringBuffer();
        sb.append(WeiXinConstant.AUTH2_ACCESS_TOKEN_URL);
        sb.append("?appid=" + WeiXinConstant.APPID);
        sb.append(WeiXinConstant.AND_SECTRET);
        sb.append("&code=" + code);
        sb.append("&grant_type=authorization_code");
        String tokenStr = HttpUtils.sendGet(sb.toString());
        Log.info("authAccessToken=" + tokenStr);
        authAccessToken = JSON.parseObject(tokenStr, AuthAccessToken.class);
        return authAccessToken;
    }

    /**
     * 用于测试微信公众号配置的url是否能连接上服务器
     *
     * @param request
     * @param response
     * @return
     */
    private String isConfigUrl(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        // 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数
        String signature = request.getParameter("signature");// 微信加密签名（token、timestamp、nonce。）
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
        Log.info("\nsignature=" + signature + "\ntimestamp=" + timestamp + "\nnonce=" + nonce + "\nechostr" + echostr);
        PrintWriter out = response.getWriter();
        // 将token、timestamp、nonce三个参数进行字典序排序
        String[] params = new String[]{"8a7b1370532d464f01532d471fb90000", timestamp, nonce};
        Arrays.sort(params);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String algorithm = "SHA-1";
        String sign = new String(
                Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
        Log.info("\nsign=" + sign);
        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(sign)) {
            Log.info("test weixin url success! echostr=" + echostr);
            return echostr;
        }
        Log.info("test weixin configure fail!");
        return null;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    private String handleWeiXinMessage(HttpServletRequest request) throws Exception {
        Map<String, Object> reqMap = WeiXinMsgUtils.parseXml(request.getInputStream());

        Log.info("reqMap:" + reqMap.toString());
        String msgType = (String) reqMap.get("msgType");
        String result = null;// 要发送的消息

        // 事件推送消息
        if (WeiXinConstant.MsgType.EVENT.equals(msgType)) {
            result = handleEventMsg(reqMap);
            // 接受普通消息
        } else {
            result = handleCommonMsg(reqMap);
        }
        Log.info("handleWeiXinMessage result = " + result);
        if (result == null) {
            // 回复空串是微信的规定，代表不回复
            return "";
        }
        return result;
    }

    /**
     * 处理事件消息
     *
     * @param reqMap 微信发过来的xml消息转为的map
     * @return 处理结果
     */
    private String handleEventMsg1(Map reqMap) throws Exception {
        String result = null;
        // 事件类型
        String eventType = (String) reqMap.get("event");
        switch (WeiXinEnum.EventType.getType(eventType)) {
            case subscribe:
                result = handleSubscribe(reqMap);
                break;
            case unsubscribe:
                break;
            case VIEW:
                break;
            case CLICK:
                break;
            case SCAN:
                break;
            case LOCATION:
                break;
            default:
        }

        return result;
    }

    /**
     * 处理关注事件消息
     *
     * @param reqMap 微信发过来的xml消息转为的map
     * @return 处理结果
     */
    private String handleSubscribe(Map reqMap) throws Exception {
        String ticket = (String) reqMap.get("ticket");

        BaseMsg msg = null;
        // 扫描带参数二维码事件
        if (ticket != null) {
            String EventKey = (String) reqMap.get("eventKey");
            QrSceneEvent event = (QrSceneEvent) WeiXinMsgUtils.mapToObject(reqMap, QrSceneEvent.class);
            // 扫码。用户未关注时，进行关注后的事件推送
            if (EventKey.startsWith("qrscene_")) {
                msg = handleSubscribe(event);
                //扫码。用户已关注时的事件推送
            } else {

            }
            msg = handleQrSceneEvent(event);
        } else {
            //根据reqMap 可以判断用户是否扫带有有参数的二维码
            BaseEvent event = (BaseEvent) WeiXinMsgUtils.mapToObject(reqMap, BaseEvent.class);
            msg = handleSubscribe(event);
            TextMessage textMessage = new TextMessage();
            textMessage.setFromUserName(event.getToUserName());
            textMessage.setToUserName(event.getFromUserName());
            textMessage.setCreateTime(System.currentTimeMillis() / 1000);
            textMessage.setContent("hello,welcome!");
            textMessage.setMsgType(WeiXinConstant.MsgType.TEXT);
            return WeiXinMsgUtils.toXml(textMessage);
        }

        return null;
    }

    /**
     * 处理取消关注事件消息
     *
     * @param reqMap 微信发过来的xml消息转为的map
     * @return 处理结果
     */
    private String handleUnSubscribe(Map reqMap) throws Exception {
        return null;
    }

    /**
     * 处理事件消息
     *
     * @param reqMap 微信发过来的xml消息转为的map
     * @return 处理结果
     */
    private String handleEventMsg(Map reqMap) throws Exception {
        BaseMsg msg = null;
        // 事件类型
        String eventType = (String) reqMap.get("event");
        // 关注
        if (WeiXinConstant.EventType.SUBSCRIBE.equals(eventType)) {

            String ticket = (String) reqMap.get("ticket");
            String EventKey = (String) reqMap.get("eventKey");
            // 扫描带参数二维码事件
            if (ticket != null) {
                QrSceneEvent event = (QrSceneEvent) WeiXinMsgUtils.mapToObject(reqMap, QrSceneEvent.class);
                // 扫码。用户未关注时，进行关注后的事件推送
                if (EventKey.startsWith("qrscene_")) {
                    msg = handleSubscribe(event);
                    //扫码。用户已关注时的事件推送
                } else {

                }
                msg = handleQrSceneEvent(event);
            } else {
                //根据reqMap 可以判断用户是否扫带有有参数的二维码
                BaseEvent event = (BaseEvent) WeiXinMsgUtils.mapToObject(reqMap, BaseEvent.class);
                msg = handleSubscribe(event);
                TextMessage textMessage = new TextMessage();
                textMessage.setFromUserName(event.getToUserName());
                textMessage.setToUserName(event.getFromUserName());
                textMessage.setCreateTime(System.currentTimeMillis() / 1000);
                textMessage.setContent("hello,welcome!");
                textMessage.setMsgType(WeiXinConstant.MsgType.TEXT);
                return WeiXinMsgUtils.toXml(textMessage);
            }

            // 取消关注
        } else if (WeiXinConstant.EventType.UNSUBSCRIBE.equals(eventType)) {
            BaseEvent event = (BaseEvent) WeiXinMsgUtils.mapToObject(reqMap, BaseEvent.class);
            msg = handleUnsubscribe(event);

            // 点击菜单拉取消息时的事件推送
        } else if (WeiXinConstant.EventType.CLICK.equals(eventType)) {
            MenuEvent event = (MenuEvent) WeiXinMsgUtils.mapToObject(reqMap, MenuEvent.class);
            msg = handleMenuClickEvent(event);

            // 点击菜单跳转链接时的事件推送
        } else if (WeiXinConstant.EventType.VIEW.equals(eventType)) {
            MenuEvent event = (MenuEvent) CollectionUtls.mapToObject(reqMap, MenuEvent.class);
            msg = handleMenuViewEvent(event);
            TextMessage textMessage = new TextMessage();
            textMessage.setFromUserName(event.getToUserName());
            textMessage.setToUserName(event.getFromUserName());
            textMessage.setCreateTime(System.currentTimeMillis() / 1000);
            textMessage.setContent("hello,welcome!");
            textMessage.setMsgType(WeiXinConstant.MsgType.TEXT);
            return WeiXinMsgUtils.toXml(textMessage);

            // 上报地理位置事件
        } else if (WeiXinConstant.EventType.LOCATION.equals(eventType)) {
            LocationEvent event = (LocationEvent) CollectionUtls.mapToObject(reqMap, LocationEvent.class);
            msg = handleLocationEvent(event);
        }
        if (msg == null) {
            return null;
        }
        return WeiXinMsgUtils.toXml(msg);
    }

    /**
     * 处理普通消息
     *
     * @param reqMap 微信发过来的xml消息转为的map
     * @return 处理结果
     */
    private String handleCommonMsg1(Map reqMap) throws Exception {
        String result = null;
        String msgType = (String) reqMap.get("MsgType");
        switch (WeiXinEnum.MsgType.getType(msgType)) {
            case event:
                result = handleEventMsg(reqMap);
                break;
            case text:
                break;
            case image:
                break;
            case video:
                break;
            case voice:
                break;
            case shortvideo:
                break;
            case link:
                break;
            case music:
                break;
            case news:
                break;
            case wxcard:
                break;
            default:
                ;
        }
        return "";
    }

    /**
     * 处理普通消息
     *
     * @param reqMap 微信发过来的xml消息转为的map
     * @return 处理结果
     */
    private String handleCommonMsg(Map reqMap) throws Exception {
        BaseMsg msg = null;
        String msgType = (String) reqMap.get("MsgType");
        // 文本消息
        if (WeiXinConstant.MsgType.TEXT.equals(msgType)) {
            TextMessage message = (TextMessage) WeiXinMsgUtils.mapToObject(reqMap, TextMessage.class);
            msg = handleTextMsg(message);
        }
        // 图片消息
        else if (WeiXinConstant.MsgType.IMAGE.equals(msgType)) {
            ImageMessage message = (ImageMessage) WeiXinMsgUtils.mapToObject(reqMap, ImageMessage.class);
            msg = handleImageMsg(message);
        }
        // 音频消息
        else if (WeiXinConstant.MsgType.VIDEO.equals(msgType)) {
            VoiceMessage message = (VoiceMessage) WeiXinMsgUtils.mapToObject(reqMap, VoiceMessage.class);
            msg = handleVoiceMsg(message);
        }
        // 视频消息
        else if (WeiXinConstant.MsgType.VIDEO.equals(msgType)) {
            VideoMessage message = (VideoMessage) WeiXinMsgUtils.mapToObject(reqMap, VideoMessage.class);
            msg = handleVideoMsg(message);
        }
        // 地理位置消息
        else if (WeiXinConstant.MsgType.LOCATION.equals(msgType)) {
            LocationMessage message = (LocationMessage) WeiXinMsgUtils.mapToObject(reqMap, LocationMessage.class);
            msg = handleLocationMsg(message);
        }
        // 链接消息
        else if (WeiXinConstant.MsgType.LINK.equals(msgType)) {
            LinkMessage message = (LinkMessage) WeiXinMsgUtils.mapToObject(reqMap, LinkMessage.class);
            msg = handleLinkMsg(message);
        }

        if (msg == null) {
            return null;
        }
        return WeiXinMsgUtils.toXml(msg);
    }
    /*******************************************下面为处理每个请求的具体逻辑入口*******************************/

    /**
     * 处理文本消息
     */
    protected BaseMsg handleTextMsg(TextMessage msg) {
        Log.info("handleTextMsg=" + JSON.toJSON(msg).toString());
        return handleDefaultMsg(msg);
    }

    /**
     * 处理图片消息
     */
    protected BaseMsg handleImageMsg(ImageMessage msg) {
        Log.info("handleImageMsg=" + JSON.toJSON(msg).toString());
        return handleDefaultMsg(msg);
    }

    /**
     * 处理语音消息
     */
    protected BaseMsg handleVoiceMsg(VoiceMessage msg) {
        Log.info("handleVoiceMsg=" + JSON.toJSON(msg).toString());
        return handleDefaultMsg(msg);
    }

    /**
     * 处理视频消息
     */
    protected BaseMsg handleVideoMsg(VideoMessage msg) {
        Log.info("handleVideoMsg=" + JSON.toJSON(msg).toString());
        return handleDefaultMsg(msg);
    }

    /**
     * 处理地理位置消息
     */
    protected BaseMsg handleLocationMsg(LocationMessage msg) {
        Log.info("handleLocationMsg=" + JSON.toJSON(msg).toString());
        return handleDefaultMsg(msg);
    }

    /**
     * 处理链接消息
     */
    protected BaseMsg handleLinkMsg(LinkMessage msg) {
        Log.info("handleLinkMsg=" + JSON.toJSON(msg).toString());
        return handleDefaultMsg(msg);
    }

    /**
     * 处理扫描带参数二维码事件
     */
    protected BaseMsg handleQrSceneEvent(QrSceneEvent event) {
        Log.info("处理扫描带参数二维码事件");
        Log.info("even_subscribe=" + JSON.toJSON(event).toString());
        // userScanQrCodeService.handleScanQrCode();
        return handleDefaultEvent(event);
    }

    /**
     * 处理上报地理位置事件
     */
    protected BaseMsg handleLocationEvent(LocationEvent event) {
        Log.info("handleLocationEvent=" + JSON.toJSON(event).toString());
        return handleDefaultEvent(event);
    }

    /**
     * 处理点击菜单拉取消息时的事件推送
     */
    protected BaseMsg handleMenuClickEvent(MenuEvent event) {
        Log.info("点击菜单拉取消息时的事件推送");
        Log.info("MenuViewEvent-click=" + JSON.toJSON(event).toString());
        return handleDefaultEvent(event);
    }

    /**
     * 处理点击菜单跳转链接时的事件推送
     */
    protected BaseMsg handleMenuViewEvent(MenuEvent event) {
        Log.info("点击菜单跳转链接时的事件推送");
        Log.info("MenuViewEvent-url=" + JSON.toJSON(event).toString());
        return handleDefaultEvent(event);
    }

    /**
     * 处理关注事件
     * 默认不回复
     */
    protected BaseMsg handleSubscribe(BaseEvent event) {
        Log.info("even_subscribe=" + JSON.toJSON(event).toString());
        // userSubscribeService.handleSubscribeService(event ,reMap );
        // String msg = userSubscribeService.displayAutoReturn();
        Log.info("even_subscribe save data success!");
        return handleDefaultEvent(event);
    }

    /**
     * 处理取消订阅事件 <br>
     * 默认不回复
     */
    protected BaseMsg handleUnsubscribe(BaseEvent event) {
        Log.info("even_unsubscribe=" + JSON.toJSON(event).toString());
        // userUnSubscribeService.handleUnSubscribe(event );
        return handleDefaultEvent(event);
    }

    /**
     * 处理消息的默认方式 <br>
     * 如果不重写该方法，则默认不返回任何消息
     */
    protected BaseMsg handleDefaultMsg(BaseMsg msg) {
        Log.info("handleDefaultMsg=" + JSON.toJSON(msg).toString());
        return null;
    }

    /**
     * 设置处理事件的默认方式 <br>
     * 如果不重写该方法，则默认不返回任何消息
     */
    protected BaseMsg handleDefaultEvent(BaseEvent event) {
        Log.info("handleDefaultEvent=" + JSON.toJSON(event).toString());
        return null;
    }
}
