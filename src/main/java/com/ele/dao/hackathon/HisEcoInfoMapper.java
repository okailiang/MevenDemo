package com.ele.dao.hackathon;


import com.ele.model.hackathon.HisEcoInfo;

public interface HisEcoInfoMapper {
    public int deleteByPrimaryKey(String logId);

    public int insert(HisEcoInfo record);

    public int insertSelective(HisEcoInfo record);

    public HisEcoInfo selectByPrimaryKey(String logId);

    public int updateByPrimaryKeySelective(HisEcoInfo record);

    public int updateByPrimaryKey(HisEcoInfo record);
}