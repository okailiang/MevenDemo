package com.ele.model.weixin;

/**
 * 文本消息对象类
 *
 * @author oukailiang
 * @create 2016-09-18 下午11:00
 */

public class TextMessage extends BaseMsg {

    /**
     * 文本消息内容
     */
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
