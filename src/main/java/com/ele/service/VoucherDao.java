package com.ele.service;

/**
 * Created by oukailiang on 16/7/21.
 */
public class VoucherDao {
//    private final static SqlSessionFactory sqlSessionFactory;
//
//    static {
//        String resource = "config/mybatis-config.xml";
//        Reader reader = null;
//        try {
//            reader = Resources.getResourceAsReader(resource);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//
//        }
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//
//    }
//
//    /**
//     *
//     * @param id Voucher的id
//     * @return 返回Voucher对象
//     * @throws IOException
//     */
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
//            System.out.print("查询id为“+id+”记录：" + voucher.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//        return voucher;
//    }
//
//    /**
//     * 获得前三条Voucher
//     * @return Voucher列表对象
//     */
//    public static List<Voucher> getTopThreeVoucher() {
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
//        return voucherList;
//    }
//    /**
//     * 插入一条Voucher
//     * @param voucher voucher对象
//     */
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
//    /**
//     * 批量插入voucher
//     *
//     * @param voucherList voucher对象列表
//     * @return 插入个数
//     */
//    public static int batchInsertVoucher(List<Voucher> voucherList) {
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        int insertNum;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            insertNum = voucherMapper.batchInsertVoucher(voucherList);
//            sqlSession.commit();
//            System.out.print("插入数据" + insertNum + "条：" + voucherList.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//        return insertNum;
//    }
//    /**
//     * 更新一条Voucher
//     * @param voucher voucher对象
//     */
//    public static void updateVoucher(Voucher voucher) {
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
//    /**
//     * 批量更新voucher
//     * @param voucherList voucher对象列表
//     * @return 更新个数
//     */
//    public static int batchUpdateVoucher(List<Voucher> voucherList){
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        int insertNum;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            insertNum = voucherMapper.batchUpdateVoucher(voucherList);
//            sqlSession.commit();
//            System.out.print("更新数据" + insertNum + "条：" + voucherList.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//        return insertNum ;
//    }
//
//    /**
//     * 删除一条Voucher
//     * @param voucher voucher对象
//     */
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
//    /**
//     * 批量删除voucher
//     * @param voucherList voucher对象列表
//     * @return 删除个数
//     */
//    public static int batchDeleteVoucher(List<Voucher> voucherList){
//        SqlSession sqlSession = null;
//        VoucherDao voucherMapper;
//        int insertNum;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            voucherMapper = sqlSession.getMapper(VoucherDao.class);
//            insertNum = voucherMapper.batchDeleteVoucher(voucherList);
//            sqlSession.commit();
//            System.out.print("删除数据" + insertNum + "条：" + voucherList.toString());
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//        return insertNum ;
//    }
}
