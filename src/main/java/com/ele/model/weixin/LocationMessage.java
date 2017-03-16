package com.ele.model.weixin;

import java.math.BigDecimal;

/**
 * 地理位置消息
 *
 * @author oukailiang
 * @create 2016-09-19 下午2:58
 */

public class LocationMessage extends BaseMsg {
    /**
     * 地理位置维度
     */
    private BigDecimal Location_X;
    /**
     * 地理位置经度
     */
    private BigDecimal Location_Y;
    /**
     * 地图缩放大小
     */
    private Integer Scale;
    /**
     * 地理位置信息
     */
    private String Label;

    public BigDecimal getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(BigDecimal location_X) {
        Location_X = location_X;
    }

    public BigDecimal getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(BigDecimal location_Y) {
        Location_Y = location_Y;
    }

    public Integer getScale() {
        return Scale;
    }

    public void setScale(Integer scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }
}
