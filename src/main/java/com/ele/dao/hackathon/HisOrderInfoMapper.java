package com.ele.dao.hackathon;

import com.ele.model.hackathon.HisOrderInfo;

public interface HisOrderInfoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(HisOrderInfo record);

    int insertSelective(HisOrderInfo record);

    HisOrderInfo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(HisOrderInfo record);

    int updateByPrimaryKey(HisOrderInfo record);
}