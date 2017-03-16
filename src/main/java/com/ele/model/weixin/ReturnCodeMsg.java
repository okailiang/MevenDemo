package com.ele.model.weixin;

/**
 * 微信返回的消息码和消息
 *
 * @author oukailiang
 * @create 2016-09-21 下午3:10
 */

public class ReturnCodeMsg {
    private int errcode;

    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
