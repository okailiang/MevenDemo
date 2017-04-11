package com.ele.model;

/**
 * @author oukailiang
 * @create 2017-04-07 上午11:10
 */

public class Order {

    private Long id;

    private Integer userCount;

    private Integer shopCount;

    public Order() {
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
}
