<%--
  Created by IntelliJ IDEA.
  User: oukailiang
  Date: 16/10/8
  Time: 下午4:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<head>
    <title></title>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script language="javascript">
        //加载
        wx.config({
            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: '${StringUtil.wrapString(requestAttributes.appId)}', // 必填，公众号的唯一标识
            timestamp: ${StringUtil.wrapString(requestAttributes.timeStamp)}, // 必填，生成签名的时间戳
            nonceStr: '${StringUtil.wrapString(requestAttributes.nonceStr)}', // 必填，生成签名的随机串
            signature: '${StringUtil.wrapString(requestAttributes.signature)}',// 必填，签名，见附录1
            jsApiList: [
                'checkJsApi',
                'chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function () {
            //支付
            wx.chooseWXPay({
                timestamp: ${StringUtil.wrapString(requestAttributes.timeStamp)}, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                nonceStr: '${StringUtil.wrapString(requestAttributes.nonceStr)}', // 支付签名随机串，不长于 32 位
                package: '${StringUtil.wrapString(requestAttributes.package)}', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                signType: '${StringUtil.wrapString(requestAttributes.signType)}', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                paySign: '${StringUtil.wrapString(requestAttributes.paySign)}', // 支付签名
                success: function (res) {
                    // 支付成功后的回调函数
                    WeixinJSBridge.log(res.err_msg);
                    //alert("支付接口:"+res.err_code + res.err_desc + res.err_msg);
                    if (!res.err_msg) {
                        //支付完后.跳转到成功页面.
                        location.href = "orderconfirm?orderId=${StringUtil.wrapString(requestAttributes.out_trade_no)}";
                    }
                }
            });
            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        });
        wx.error(function (res) {
            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        });
        wx.checkJsApi({
            jsApiList: ['chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function (res) {
                //alert("检测接口:"+res.err_msg);
            }
        });

        //        function invokeWeixinPay(json) {
        //            var jsParam = json.jsParam;
        //            WeixinJSBridge.invoke('getBrandWCPayRequest', {
        //                "appId": jsParam.appid,
        //                "timeStamp": jsParam.timeStamp,
        //                "nonceStr": jsParam.nonceStr,
        //                "package": jsParam.packageInfo,
        //                "signType": jsParam.signType,
        //                "paySign": jsParam.paySign
        //            }, function (res) {
        //                if (res.err_msg == "get_brand_wcpay_request:ok") {
        //                    //跳转到交易结果页面
        //                    document.location.href = "***Servlet?type=***Result&o***No=" + json.o * * * No + "&r**t=" + json.r * * t;
        //                } else {
        //                    //展示交易结果
        //                }
        //            });
        //        }

        function getUrlParam(name) {
            //构造一个含有目标参数的正则表达式对象
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            //匹配目标参数
            var r = window.location.search.substr(1).match(reg);
            //返回参数值
            if (r != null)
                return unescape(r[2]);
            return null;
        }

        function onBridgeReady() {
            var appId = getUrlParam('appId');
            var timeStamp = getUrlParam('timeStamp');
            var nonceStr = getUrlParam('nonceStr');
            var Package = getUrlParam('package');
            var signType = getUrlParam('signType');
            var paySign = getUrlParam('paySign');
            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                "appId": appId,//"wx2421b1c4370ec43b", //公众号名称，由商户传入
                "timeStamp": timeStamp,//"1395712654", //时间戳，自1970年以来的秒数
                "nonceStr": nonceStr,//"e61463f8efa94090b1f366cccfbbb444", //随机串
                "package": Package,//"prepay_id=u802345jgfjsdfgsdg888",
                "signType": signType,//"MD5", //微信签名方式:
                "paySign": paySign,//"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
            }, function (res) { // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回  ok，但并不保证它绝对可靠。
                //alert(res.err_msg);
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    alert("支付成功");
                }
                if (res.err_msg == "get_brand_wcpay_request:cancel") {
                    alert("交易取消");
                }
                if (res.err_msg == "get_brand_wcpay_request:fail") {
                    alert("支付失败");
                }
            });
        }

        function callPay() {
            if (typeof WeixinJSBridge == "undefined") {
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
                            false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
            } else {
                onBridgeReady();
            }
        }
    </script>
</head>
<body>

</body>
</html>
