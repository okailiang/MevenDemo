package com.ele.controller;

import com.ele.constant.WeiXinConstant;
import com.ele.model.weixin.UnifiedOrderParam;
import com.ele.util.HttpUtils;
import com.ele.util.WeiXinMsgUtils;
import com.ele.util.WeiXinUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信端请求服务器相关资源处理
 *
 * @author oukailiang
 * @create 2016-09-22 上午11:53
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeiXinReqAction {
    private static Logger Log = Logger.getLogger(WeiXinReqAction.class);

    /**
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "saveFile.do", method = RequestMethod.GET)
    public String saveFile(HttpServletRequest request, ModelMap model) {

        return "";
    }

    /**
     * 微信支付
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "weixinPrePay.do")
    @ResponseBody
    public static String weixinPrePay(HttpServletRequest request) {
        String sign = null;
        try {
            UnifiedOrderParam unifiedOrderParam = new UnifiedOrderParam();
            unifiedOrderParam.setAppid(WeiXinConstant.APPID);
            unifiedOrderParam.setMch_id(WeiXinConstant.MCHID);
            unifiedOrderParam.setDevice_info("WEB");
            unifiedOrderParam.setNonce_str(WeiXinUtils.getNonceStr());

            unifiedOrderParam.setBody("a4print-文件打印");
            //unifiedOrderParam.setDetail("");
            unifiedOrderParam.setOut_trade_no("2016101234567");
            unifiedOrderParam.setTotal_fee(100);
            unifiedOrderParam.setSpbill_create_ip("139.129.18.198");
            unifiedOrderParam.setNotify_url(WeiXinConstant.NOTIFY_URL);
            unifiedOrderParam.setTrade_type("JSAPI");
            unifiedOrderParam.setOpenid("oL3Vmw0AMBiZsqF2wwHxRSSxy47c");

            //对象转map
            Map<String, Object> paramMap = WeiXinMsgUtils.objectToMap(unifiedOrderParam);
            //签名
            sign = WeiXinUtils.getMd5Sign(paramMap, WeiXinConstant.PAY_KEY);
            unifiedOrderParam.setSign(sign);
            //
            paramMap.put("sign", sign);
            String param = WeiXinUtils.toXml(paramMap);
            System.out.println(param);
            //请求统一下单
            InputStream result = HttpUtils.sendPost1(WeiXinConstant.UNIFIEDORDER_URL, param);
            //解析result获得prepay_id
            Map map = WeiXinUtils.parseXml(result);

            String prepayId = String.valueOf(map.get("prepay_id"));
            //调用付款空间签名
            String paySign = getPaySign(prepayId);
            System.out.println("prepayId=" + prepayId);
            //
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获得付款请求参数md5签名
     *
     * @param prepayId
     * @return
     */
    private static String getPaySign(String prepayId) {
        Map<String, Object> payPap = new HashMap<String, Object>();
        payPap.put("appId", WeiXinConstant.APPID);
        payPap.put("timeStamp", WeiXinUtils.getTimeStamp());
        payPap.put("nonceStr", WeiXinUtils.getNonceStr());
        payPap.put("package", "prepay_id=" + prepayId);
        payPap.put("signType", "MD5");

        return WeiXinUtils.getMd5Sign(payPap, WeiXinConstant.PAY_KEY);
    }

    public static void main(String[] args) {
        HttpServletRequest request = null;
        weixinPrePay(request);
    }
}
