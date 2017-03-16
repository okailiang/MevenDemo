package com.ele.dao.hackathon;

import com.ele.model.hackathon.NextEcoInfo;

public interface NextEcoInfoMapper {
    int deleteByPrimaryKey(String logId);

    int insert(NextEcoInfo record);

    int insertSelective(NextEcoInfo record);

    NextEcoInfo selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(NextEcoInfo record);

    int updateByPrimaryKey(NextEcoInfo record);
}