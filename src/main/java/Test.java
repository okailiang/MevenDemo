import com.ele.dao.hackathon.HisEcoEnvMapper;
import com.ele.model.hackathon.HisEcoEnv;
import com.ele.service.VoucherDao;
import com.ele.model.Voucher;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by oukailiang on 16/7/20.
 */
public class Test {
    public static int count = 0;
    private final static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "config/mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    //
//    public static Voucher findVoucherById(long id) throws IOException {
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        Voucher voucher;
//        try {//String resource = "config/mybatis-config.xml";
//            //InputStream inputStream = Resources.getResourceAsStream(resource);
//            //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//            sqlSession = sqlSessionFactory.openSession();
//            //Voucher voucher = sqlSession.selectOne("com.ele.getOne", 2267677);
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            voucher = voucherMapper.findVoucherById(id);
//            System.out.print("查询id为“+id+”记录："+voucher.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//        return voucher;
//    }
//    public static void getTopThreeVoucher() {
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        List<Voucher> voucherList;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            voucherList = voucherMapper.getTopThreeVoucher();
//            sqlSession.commit();
//            System.out.print("查询前" + voucherList.size() + "条：" + voucherList.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//    }
//
//    public static void insertVoucher(Voucher voucher) {
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            int insertNum = voucherMapper.insertVoucher(voucher);
//            sqlSession.commit();
//            System.out.print("插入数据" + insertNum + "条：" + voucher.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//    }
//
//    public static void updateVoucher(Voucher voucher){
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            int insertNum = voucherMapper.updateVoucher(voucher);
//            sqlSession.commit();
//            System.out.print("更新数据" + insertNum + "条：" + voucher.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//    }
//
//    public static void deleteVoucher(Voucher voucher) {
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            int insertNum = voucherMapper.deleteVoucher(voucher);
//            sqlSession.commit();
//            System.out.print("删除数据" + insertNum + "条：" + voucher.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//    }
    public static void handleList(List<Voucher> list) {
        //int len = list.size();
        for (int i = 0; i < list.size(); i++) {
            count++;
        }
//        for(Voucher voucher:list){
//            count++;
//        }
    }

    public static void insertHisEcoEnv() {
        SqlSession sqlSession = null;
        HisEcoEnvMapper hisEcoEnvMapper;
        try {
            sqlSession = sqlSessionFactory.openSession();
            hisEcoEnvMapper = sqlSession.getMapper(HisEcoEnvMapper.class);
            for (int i = 0; i < 12; i++) {
                List<HisEcoEnv> hisEcoEnvList = getHisEcoEnvList1(0);
                List<HisEcoEnv> subHisEcoEnvList;
                int size = hisEcoEnvList.size();
                System.out.println("size" + i + "=" + size);
                int index = 0;
                int lastIndex = 0;
                //for (HisEcoEnv hisEcoEnv : hisEcoEnvList) {
                while (true) {
                    index++;
                    if (index % 2000 == 0 || index == size) {
                        subHisEcoEnvList = getHisEcoEnvList().subList(lastIndex, index);
                        hisEcoEnvMapper.batchInsertHisEcoEnv(subHisEcoEnvList);
                        sqlSession.commit();
                        lastIndex = index;
                        System.out.print("插入数据" + index + "条：");
                    }
                    if (index == size) {
                        break;
                    }
                }

            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public static List<Voucher> getVoucherList() {
        List<Voucher> voucherList = new ArrayList<Voucher>();
        for (int i = 0; i < 1000000; i++) {
            Voucher voucher = new Voucher();

            voucher.setId(new Long(2));
            voucher.setVoucherNo("FFFFFFFFFF");
            voucher.setAmount(BigDecimal.valueOf(6.00));
            voucher.setActiveId(new Long(245));
            voucher.setBatchNo(null);
            voucher.setConsumerType(1);
            voucher.setActiveName("默认全国新用户优惠卷");
            voucher.setActiveType(6);
            voucher.setStatus(1);
            voucher.setVoucherBeginTime(new Date());
            voucher.setVoucherEndTime(new Date());
            voucher.setActiveStartTime(new Date());
            voucher.setActiveEndTime(new Date());
            voucher.setActiveRule("");
            voucher.setFull(new Long(12));
            voucher.setType(5);
            voucher.setUseTimes(1);
            voucher.setVersion(0);
            voucher.setCreateTime(new Date());
            voucher.setLastModified(new Date());
            voucher.setModifiedBy("sys");
            voucher.setBusinessType(1);
            voucher.setCityId(new Long(0));
            voucher.setStrategyCount(0);
            voucherList.add(voucher);
        }
        return voucherList;
    }

    public static List<HisEcoEnv> getHisEcoEnvList() {
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

    public static List<HisEcoEnv> getHisEcoEnvList1(int index) {
        List<HisEcoEnv> hisEcoEnvList = new ArrayList<HisEcoEnv>();
        HisEcoEnv hisEcoEnv;
        File file = new File("/Users/oukailiang/Downloads/hackathon/E_data/txt/his_eco_env" + index + ".txt");
        BufferedReader br = null;
        FileReader fr = null;
        String line;
        int count = 0;
        try {
            //读
            fr = new FileReader(file);
            br = new BufferedReader(fr);//构造一个BufferedReader类来读取文件
            //String oneRow = br.readLine();
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

    public static String removeColon(String colValue) {
        int len = colValue.length();
        return colValue.substring(1, len - 1);
    }


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        // Voucher voucher;

        long end = System.currentTimeMillis();
        System.out.println("time=" + (end - start));

        long start1 = System.currentTimeMillis();
        //handleList(getVoucherList());
        long end1 = System.currentTimeMillis();
        System.out.println("handle time=" + (end1 - start1) + "\ncount=" + count);
        //voucherList.add(voucher);

        //voucherList.add(voucher1);
        // Voucher voucher1 = VoucherDao.findVoucherById(1);
        //voucher1.setId(new Long(3));
        // voucherList.add(voucher1);
        //批量保存
        //VoucherDao.batchInsertVoucher(voucherList);
        //查询三条数据
        //List<Voucher> vouchers = VoucherDao.getTopThreeVoucher();
        //for (Voucher v : vouchers) {
        //    v.setActiveName("呵呵!");
        //}
        //批量更新
        // VoucherDao.batchUpdateVoucher(vouchers);
        //批量删除
        ///VoucherDao.batchDeleteVoucher(vouchers);

        //voucher = findVoucherById(1);
        //getTopThreeVoucher();
        //VoucherDao.insertVoucher(voucher);
        //
        //voucher.setActiveName("测试数据");
        //updateVoucher(voucher);
        //findVoucherById(voucher.getId());
        //deleteVoucher(voucher);


        //hsckathon
        insertHisEcoEnv();
    }
}
