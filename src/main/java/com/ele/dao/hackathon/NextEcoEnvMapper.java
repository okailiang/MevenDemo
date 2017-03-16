package com.ele.dao.hackathon;

import com.ele.model.hackathon.NextEcoEnv;

public interface NextEcoEnvMapper {
    int deleteByPrimaryKey(String listId);

    int insert(NextEcoEnv record);

    int insertSelective(NextEcoEnv record);

    NextEcoEnv selectByPrimaryKey(String listId);

    int updateByPrimaryKeySelective(NextEcoEnv record);

    int updateByPrimaryKey(NextEcoEnv record);
}