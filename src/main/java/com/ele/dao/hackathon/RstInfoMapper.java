package com.ele.dao.hackathon;


import com.ele.model.hackathon.RstInfo;

public interface RstInfoMapper {
    int deleteByPrimaryKey(String restaurantId);

    int insert(RstInfo record);

    int insertSelective(RstInfo record);

    RstInfo selectByPrimaryKey(String restaurantId);

    int updateByPrimaryKeySelective(RstInfo record);

    int updateByPrimaryKey(RstInfo record);
}