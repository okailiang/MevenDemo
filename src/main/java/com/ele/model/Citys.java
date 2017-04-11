package com.ele.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oukailiang
 * @create 2017-04-10 下午7:01
 */

public class Citys {
    private int id;
    private int districtCode;
    private String name;
    private int level;
    private int type;
    private String pinyin;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private  int prentId;
    private String abbr;
    private String fullName;
    private List<Citys> directCityList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(int districtCode) {
        this.districtCode = districtCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public int getPrentId() {
        return prentId;
    }

    public void setPrentId(int prentId) {
        this.prentId = prentId;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Citys> getDirectCityList() {
        return directCityList;
    }

    public void setDirectCityList(List<Citys> directCityList) {
        this.directCityList = directCityList;
    }
}
