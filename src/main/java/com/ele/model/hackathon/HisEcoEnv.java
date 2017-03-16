package com.ele.model.hackathon;

import java.lang.Integer;
import java.lang.String;
import java.math.BigDecimal;

public class HisEcoEnv {
    private String listId;//                列表ID，此表主键
    private Integer isSelect;              //该列表是否是经过用户筛选的列表，1为是，0为否
    private Integer dayNo;                 //日期号，本数据集中日期号从1到154
    private Integer minutes;               //该订单产生的的分钟数，hour*60+minute计算得到
    private String elemeDeviceId;        //对应的设备ID
    private Integer isNew;                //改设备是否当天首次来访
    private BigDecimal x;                     //浏览用户所在位置的横坐标
    private BigDecimal y;                    //浏览用户所在位置的纵坐标
    private String userId;              //用户的账号ID，未登陆为NULL
    private String networkType;         // 网络类型
    private String platform;            //操作系统，Android/iOS
    private String brand;              //手机品牌
    private String model;             //手机型号
    private String networkOperator;       //网络运营商，yd:移动，lt: 联通，dx: 典型
    private String resolution;               //手机分辨率
    private String channel;                  //APP的下载渠道

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public Integer getDayNo() {
        return dayNo;
    }

    public void setDayNo(Integer dayNo) {
        this.dayNo = dayNo;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getElemeDeviceId() {
        return elemeDeviceId;
    }

    public void setElemeDeviceId(String elemeDeviceId) {
        this.elemeDeviceId = elemeDeviceId;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}