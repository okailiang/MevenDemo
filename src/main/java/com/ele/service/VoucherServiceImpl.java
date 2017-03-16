package com.ele.service;

import com.ele.dao.VoucherDao;
import com.ele.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by oukailiang on 16/7/21.
 */
@Service
public class VoucherServiceImpl implements IVoucherService {
    @Autowired
    private VoucherDao voucherDao;

    /**
     * 通过voucher的id查找voucher
     *
     * @param id voucher的id
     * @return Voucher对象
     */
    @Override
    public Voucher findVoucherById(long id) {
        return voucherDao.findVoucherById(id);
    }

    /**
     * 查询Voucher表中的前三条数据
     * @return 返回Voucher对象列表
     */
    @Override
    public List<Voucher> getTopThreeVouchers() {
        return voucherDao.getTopThreeVoucher();
    }

    @Override
    public int insertVoucher(Voucher voucher) {
        return voucherDao.insertVoucher(voucher);
    }

    @Override
    public int batchInsertVoucher(List<Voucher> voucherList) {
        return voucherDao.batchInsertVoucher(voucherList);
    }

    @Override
    public int updateVoucher(Voucher voucher) {
        return voucherDao.updateVoucher(voucher);
    }

    @Override
    public int batchUpdateVoucher(List<Voucher> voucherList) {
        return voucherDao.batchUpdateVoucher(voucherList);
    }

    @Override
    public int deleteVoucher(Voucher voucher) {
        return voucherDao.deleteVoucher(voucher);
    }

    @Override
    public int batchDeleteVoucher(List<Voucher> voucherList) {
        return voucherDao.batchDeleteVoucher(voucherList);
    }


}
