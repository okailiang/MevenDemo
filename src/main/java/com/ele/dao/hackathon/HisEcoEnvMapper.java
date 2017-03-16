package com.ele.dao.hackathon;

import com.ele.model.hackathon.HisEcoEnv;

import java.util.List;

public interface HisEcoEnvMapper {
    int deleteByPrimaryKey(String listId);

    int insert(HisEcoEnv record);

    int insertSelective(HisEcoEnv record);

    HisEcoEnv selectByPrimaryKey(String listId);

    int updateByPrimaryKeySelective(HisEcoEnv record);

    int updateByPrimaryKey(HisEcoEnv record);

    int batchInsertHisEcoEnv(List<HisEcoEnv> hisEcoEnvList);
}