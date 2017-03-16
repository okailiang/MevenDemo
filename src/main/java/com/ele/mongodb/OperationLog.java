package com.ele.mongodb;

import java.util.Date;

/**
 * @author oukailiang
 * @create 2016-09-29 下午6:48
 */

public class OperationLog {
    private Long id;

    private Long creatorId;

    private String creator;

    private String methodName;

    private String className;

    private String ipAddress;

    private Date createTime;

    private Integer levelOneModule;

    private Integer levelTwoModule;

    private Date lastModified;

    private Long costTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLevelOneModule() {
        return levelOneModule;
    }

    public void setLevelOneModule(Integer levelOneModule) {
        this.levelOneModule = levelOneModule;
    }

    public Integer getLevelTwoModule() {
        return levelTwoModule;
    }

    public void setLevelTwoModule(Integer levelTwoModule) {
        this.levelTwoModule = levelTwoModule;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }
}
