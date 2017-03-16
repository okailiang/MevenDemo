package com.ele.service;

import com.ele.model.hackathon.*;

import java.util.List;

/**
 * @author oukailiang
 * @create 2016-10-12 下午2:40
 */
public interface HackathonService {
    int batchInsertHisEcoEnv(List<HisEcoEnv> hisEcoEnvList);

    int batchInsertHisEcoInfo(List<HisEcoInfo> hisEcoInfoList);

    int batchInsertHisOrderInfo(List<HisOrderInfo> hisOrderInfoList);

    int batchInsertRstInfo(List<RstInfo> rstInfoList);

    int batchInsertNextEcoEnv(List<NextEcoEnv> nextEcoEnvList);

    int batchInsertNextEcoInfo(List<NextEcoInfo> nextEcoInfoList);
}
