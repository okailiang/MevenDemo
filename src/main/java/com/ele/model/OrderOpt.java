package com.ele.model;

/**
 * @author oukailiang
 * @create 2017-04-14 下午3:38
 */

public class OrderOpt{
    private Long id;

    private Integer userCount;

    private Integer shopCount;

    private String orderNo;

    public OrderOpt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
