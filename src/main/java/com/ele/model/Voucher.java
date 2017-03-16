package com.ele.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by oukailiang on 16/7/19.
 */
public class Voucher implements Serializable, Cloneable {
    private static final long serialVersionUID = -8135761015450794455L;
    private Long id;
    private String voucherNo;
    private BigDecimal amount;
    private Long activeId;
    private String batchNo;
    private Integer consumerType;
    private String activeName;
    private Integer activeType;
    private Integer status;
    private Date voucherBeginTime;
    private Date voucherEndTime;
    private Date activeStartTime;
    private Date activeEndTime;
    private String activeImageUrl;
    private String activeRule;
    private Long full;
    private Integer type;
    private Integer useTimes;
    private Integer version;
    private Date createTime;
    private Date lastModified;
    private String modifiedBy;
    private Integer businessType;
    private String phoneNum;
    private Long cityId;
    private Integer strategyCount;

    public Voucher() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(Integer consumerType) {
        this.consumerType = consumerType;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public Integer getActiveType() {
        return activeType;
    }

    public void setActiveType(Integer activeType) {
        this.activeType = activeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getVoucherBeginTime() {
        return voucherBeginTime;
    }

    public void setVoucherBeginTime(Date voucherBeginTime) {
        this.voucherBeginTime = voucherBeginTime;
    }

    public Date getVoucherEndTime() {
        return voucherEndTime;
    }

    public void setVoucherEndTime(Date voucherEndTime) {
        this.voucherEndTime = voucherEndTime;
    }

    public Date getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(Date activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public Date getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(Date activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public String getActiveImageUrl() {
        return activeImageUrl;
    }

    public void setActiveImageUrl(String activeImageUrl) {
        this.activeImageUrl = activeImageUrl;
    }

    public String getActiveRule() {
        return activeRule;
    }

    public void setActiveRule(String activeRule) {
        this.activeRule = activeRule;
    }

    public Long getFull() {
        return full;
    }

    public void setFull(Long full) {
        this.full = full;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getStrategyCount() {
        return strategyCount;
    }

    public void setStrategyCount(Integer strategyCount) {
        this.strategyCount = strategyCount;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", voucherNo='" + voucherNo + '\'' +
                ", amount=" + amount +
                ", activeId=" + activeId +
                ", batchNo='" + batchNo + '\'' +
                ", consumerType=" + consumerType +
                ", activeName='" + activeName + '\'' +
                ", activeType=" + activeType +
                ", status=" + status +
                ", voucherBeginTime=" + voucherBeginTime +
                ", voucherEndTime=" + voucherEndTime +
                ", activeStartTime=" + activeStartTime +
                ", activeEndTime=" + activeEndTime +
                ", activeImageUrl='" + activeImageUrl + '\'' +
                ", activeRule='" + activeRule + '\'' +
                ", full=" + full +
                ", type=" + type +
                ", useTimes=" + useTimes +
                ", version=" + version +
                ", createTime=" + createTime +
                ", lastModified=" + lastModified +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", businessType=" + businessType +
                ", phoneNum='" + phoneNum + '\'' +
                ", cityId=" + cityId +
                ", strategyCount=" + strategyCount +
                '}';
    }

    public Object clone() {
        Voucher o = null;
        try {
            o = (Voucher) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
