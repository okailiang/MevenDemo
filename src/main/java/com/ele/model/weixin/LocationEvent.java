package com.ele.model.weixin;

import java.math.BigDecimal;

/**
 * 上报地理位置事件
 *
 * @author oukailiang
 * @create 2016-09-19 下午12:45
 */

public class LocationEvent extends BaseEvent {
    /**
     * 地理位置纬度
     */
    private BigDecimal Latitude;
    /**
     * 地理位置经度
     */
    private BigDecimal Longitude;

    /**
     * 地理位置精度
     */
    private BigDecimal Precision;

    public BigDecimal getLatitude() {
        return Latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        Latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return Longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        Longitude = longitude;
    }

    public BigDecimal getPrecision() {
        return Precision;
    }

    public void setPrecision(BigDecimal precision) {
        Precision = precision;
    }
}
