package com.ele.service;

import com.ele.dao.hackathon.*;
import com.ele.model.hackathon.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oukailiang
 * @create 2016-10-12 下午2:41
 */
@Service
public class HackathonServiceImpl implements HackathonService {
    @Autowired
    private HisEcoEnvMapper hisEcoEnvMapper;
    @Autowired
    private HisEcoInfoMapper hisEcoInfoMapper;
    @Autowired
    private HisOrderInfoMapper hisOrderInfoMapper;
    @Autowired
    private RstInfoMapper rstInfoMapper;
    @Autowired
    private NextEcoEnvMapper nextEcoEnvMapper;
    @Autowired
    private NextEcoInfoMapper nextEcoInfoMapper;

    @Override
    public int batchInsertHisEcoEnv(List<HisEcoEnv> hisEcoEnvList) {
        //hisEcoEnvList = getHisEcoEnvList();

        int count = hisEcoEnvMapper.batchInsertHisEcoEnv(getHisEcoEnvList().subList(0, 10));
        System.out.println("insert count=" + count);
        //getHisEcoEnvList();
        return 0;
    }

    @Override
    public int batchInsertHisEcoInfo(List<HisEcoInfo> hisEcoInfoList) {
        return 0;
    }

    @Override
    public int batchInsertHisOrderInfo(List<HisOrderInfo> hisOrderInfoList) {
        return 0;
    }

    @Override
    public int batchInsertRstInfo(List<RstInfo> rstInfoList) {
        return 0;
    }

    @Override
    public int batchInsertNextEcoEnv(List<NextEcoEnv> nextEcoEnvList) {
        return 0;
    }

    @Override
    public int batchInsertNextEcoInfo(List<NextEcoInfo> nextEcoInfoList) {
        return 0;
    }

    private List<HisEcoEnv> getHisEcoEnvList() {
        List<HisEcoEnv> hisEcoEnvList = new ArrayList<HisEcoEnv>();
        HisEcoEnv hisEcoEnv;
        File file = new File("/Users/oukailiang/Downloads/hackathon/E_data/his_eco_env.txt");
        BufferedReader br = null;
        FileReader fr = null;
        String line;
        int count = 0;
        try {
            //读
            fr = new FileReader(file);
            br = new BufferedReader(fr);//构造一个BufferedReader类来读取文件
            String oneRow = br.readLine();
            while ((line = br.readLine()) != null) {
                hisEcoEnv = new HisEcoEnv();
                String col[] = line.split("\t");
                for (int i = 0; i < col.length; i++) {
                    if (col[i].indexOf("\"") > -1) {
                        col[i] = removeColon(col[i]);
                    }
                }
                hisEcoEnv.setListId(col[0]);
                hisEcoEnv.setIsSelect(Integer.parseInt(col[1]));
                hisEcoEnv.setDayNo(Integer.parseInt(col[1]));
                hisEcoEnv.setMinutes(Integer.parseInt(col[1]));
                hisEcoEnv.setElemeDeviceId(col[4]);
                hisEcoEnv.setIsNew(Integer.parseInt(col[1]));
                hisEcoEnv.setX(new BigDecimal(col[6]));
                hisEcoEnv.setY(new BigDecimal(col[7]));
                hisEcoEnv.setUserId(col[8]);
                hisEcoEnv.setNetworkType(col[9]);
                hisEcoEnv.setPlatform(col[10]);
                hisEcoEnv.setBrand(col[11]);
                hisEcoEnv.setModel(col[12]);
                hisEcoEnv.setNetworkOperator(col[13]);
                hisEcoEnv.setResolution(col[14]);
                hisEcoEnv.setChannel(col[15]);
                //hisEcoEnvMapper.insert(hisEcoEnv);
                hisEcoEnvList.add(hisEcoEnv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return hisEcoEnvList;
    }

    private String removeColon(String colValue) {
        int len = colValue.length();
        return colValue.substring(1, len - 1);
    }
}
