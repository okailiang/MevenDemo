package com.ele.service;

import com.ele.model.Voucher;

import java.util.List;

/**
 * Created by oukailiang on 16/7/21.
 */
public interface IVoucherService {
    /**
     * 通过voucher的id查找voucher
     *
     * @param id voucher的id
     * @return Voucher对象
     */
    public Voucher findVoucherById(long id);

    /**
     * 查询Voucher表中的前三条数据
     *
     * @return 返回Voucher对象列表
     */
    public List<Voucher> getTopThreeVouchers();

    public int insertVoucher(Voucher voucher);

    public int batchInsertVoucher(List<Voucher> voucherList);

    public int updateVoucher(Voucher voucher);

    public int batchUpdateVoucher(List<Voucher> voucherList);

    public int deleteVoucher(Voucher voucher);

    public int batchDeleteVoucher(List<Voucher> voucherList);
}
