package com.ele.model.weixin;

/**
 * 自定义菜单事件
 *
 * @author oukailiang
 * @create 2016-09-19 下午12:03
 */

public class MenuEvent extends BaseEvent {
    /**
     * 1.点击菜单拉取消息时的事件推送,事件KEY值，与自定义菜单接口中KEY值对应
     * 2.点击菜单跳转链接时的事件推送,事件KEY值，设置的跳转URL
     */
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
