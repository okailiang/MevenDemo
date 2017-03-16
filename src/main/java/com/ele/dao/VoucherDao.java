package com.ele.dao;

import com.ele.model.Voucher;

import java.util.List;

/**
 * Created by oukailiang on 16/7/20.
 */
public interface VoucherDao {
    public Voucher findVoucherById(long id);

    public List<Voucher> getTopThreeVoucher();

    public int insertVoucher(Voucher voucher);

    public int batchInsertVoucher(List<Voucher> voucherList);

    public int updateVoucher(Voucher voucher);

    public int batchUpdateVoucher(List<Voucher> voucherList);

    public int deleteVoucher(Voucher voucher);

    public int batchDeleteVoucher(List<Voucher> voucherList);
}
