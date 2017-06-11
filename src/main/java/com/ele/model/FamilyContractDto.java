package com.ele.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author oukailiang
 * @create 2017-06-07 上午11:43
 */
public class FamilyContractDto {

    private Long id;

    private String contractCode;

    private Integer contractType;

    private Integer isValid;

    private Date beginTime;

    private Date endTime;

    private Date signTime;

    private Integer isUploadContract;

    private Double shareRatio;

    private BigDecimal deposit;

    private BigDecimal transferFee;

    private Double accountLimit;

    private Double returnRation;

    private String publicAccount;

    private Long bankNumbers;

    private Integer accountType;

    private String bankName;

    private String branchBankName;

    private String branchBankProvince;

    private String branchBankCity;

    private Long agentId;

    private String attachmentUrl;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    // 代理城市
    private String agentCityName;

    private Long agentCityId;

    private Integer agentCityLevel;

    // 代理商公司信息
    private String agentName;

    private String companyTelephone;

    private String companyName;

    private String companyAddress;

    private String legalPersonEmail;

    private String uniformSocialCreditCode;

    public Integer getAgentCityLevel() {
        return agentCityLevel;
    }

    public void setAgentCityLevel(Integer agentCityLevel) {
        this.agentCityLevel = agentCityLevel;
    }

    public String getAgentCityName() {
        return agentCityName;
    }

    public void setAgentCityName(String agentCityName) {
        this.agentCityName = agentCityName;
    }

    public Long getAgentCityId() {
        return agentCityId;
    }

    public void setAgentCityId(Long agentCityId) {
        this.agentCityId = agentCityId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getLegalPersonEmail() {
        return legalPersonEmail;
    }

    public void setLegalPersonEmail(String legalPersonEmail) {
        this.legalPersonEmail = legalPersonEmail;
    }

    public String getUniformSocialCreditCode() {
        return uniformSocialCreditCode;
    }

    public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
        this.uniformSocialCreditCode = uniformSocialCreditCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Double getShareRatio() {
        return shareRatio;
    }

    public void setShareRatio(Double shareRatio) {
        this.shareRatio = shareRatio;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public Double getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(Double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public Double getReturnRation() {
        return returnRation;
    }

    public void setReturnRation(Double returnRation) {
        this.returnRation = returnRation;
    }

    public String getPublicAccount() {
        return publicAccount;
    }

    public void setPublicAccount(String publicAccount) {
        this.publicAccount = publicAccount;
    }

    public Long getBankNumbers() {
        return bankNumbers;
    }

    public void setBankNumbers(Long bankNumbers) {
        this.bankNumbers = bankNumbers;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchBankName() {
        return branchBankName;
    }

    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    public String getBranchBankProvince() {
        return branchBankProvince;
    }

    public void setBranchBankProvince(String branchBankProvince) {
        this.branchBankProvince = branchBankProvince;
    }

    public String getBranchBankCity() {
        return branchBankCity;
    }

    public void setBranchBankCity(String branchBankCity) {
        this.branchBankCity = branchBankCity;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getIsUploadContract() {
        return isUploadContract;
    }

    public void setIsUploadContract(Integer isUploadContract) {
        this.isUploadContract = isUploadContract;
    }
}
