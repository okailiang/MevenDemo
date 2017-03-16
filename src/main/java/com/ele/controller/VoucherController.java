package com.ele.controller;

import com.alibaba.fastjson.JSON;
import com.ele.demo.MyAES;
import com.ele.model.Voucher;
import com.ele.service.IVoucherService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by oukailiang on 16/7/21.
 */
@Controller
@RequestMapping("/")
public class VoucherController {

    private static Logger Log = Logger.getLogger(VoucherController.class);
    @Autowired
    private IVoucherService iVoucherService;

    @RequestMapping(value = "voucherList.do", method = RequestMethod.GET)
    public ModelAndView getVoucherById(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Log.info("voucherList getSessoin=" + session.getAttribute("okl"));
            session.setAttribute("okl", "voucherListoukailiang");
            Log.info("voucherList sessionId=" + session.getId());
        }

        HttpSession session1 = request.getSession(true);
        Log.info("voucherList NewsessionId=" + session1.getId());
        session1.setAttribute("okl", "voucherListnewoukailiang");
        String id = (String) request.getParameter("id");
        String openId = (String) request.getParameter("openId");
        List<Voucher> voucherList = new ArrayList<Voucher>();

        //Voucher voucher = iVoucherService.findVoucherById(id);
        //voucherList.add(voucher);
        //model.addAttribute("voucherList", voucherList);
        model.addAttribute("key", "你好");
        model.addAttribute("openId", openId);

        return new ModelAndView("voucherList");
    }

    /**
     * 查询Voucher表中的前三条数据
     *
     * @return
     */
    @RequestMapping(value = "getTopThreeVouchers.do", method = RequestMethod.GET)
    @ResponseBody
    public String getTopThreeVouchers() {
        //  List<Voucher> voucherList = iVoucherService.getTopThreeVouchers();
        // System.out.print(JSON.toJSONString(voucherList));
        return JSON.toJSONString("");
    }

    /**
     * 通过voucher的id查找voucher
     *
     * @param request voucher的id
     * @return Voucher对象的json格式字符串
     */
    @RequestMapping(value = "getVoucherById.do", method = RequestMethod.GET)
    @ResponseBody
    public String getVoucherById(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Log.info("getVoucherById getSessoin=" + session.getAttribute("okl"));
            session.setAttribute("okl", "getVoucherByIdoukailiang");
            Log.info("getAuth sessionId=" + session.getId());
        }

        HttpSession session1 = request.getSession(true);
        Log.info("getVoucherById NewsessionId=" + session1.getId());
        session1.setAttribute("okl", "getVoucherByIdnewoukailiang");
        //Voucher voucher = iVoucherService.findVoucherById(id);
        return JSON.toJSONString("");
    }

    /**
     * 向数据库中插入一条Voucher 数据
     *
     * @return
     */
    @RequestMapping(value = "insertVoucher.do", method = RequestMethod.POST)
    @ResponseBody
    public String insertVoucher() {
        Voucher voucher = getVoucher();
        int num = iVoucherService.insertVoucher(voucher);
        return JSON.toJSONString(num);
    }

    /**
     * 通过voucher的id删除voucher
     *
     * @param id voucher的id
     * @return Voucher对象的json格式字符串
     */
    @RequestMapping(value = "deleteVoucher.do", method = RequestMethod.GET)
    @ResponseBody
    public String deleteVoucher(@RequestParam(value = "id") String id) {
        Voucher voucher = new Voucher();

        if (id != null && id.length() > 0) {
            voucher.setId(Long.valueOf(id));
        }
        int num = iVoucherService.deleteVoucher(voucher);
        return JSON.toJSONString(num);
    }

    /**
     * 通过voucher的ids批量删除voucher
     *
     * @param ids voucher的ids
     * @return Voucher对象的json格式字符串
     */
    @RequestMapping(value = "deleteVouchers.do", method = RequestMethod.GET)
    @ResponseBody
    public String deleteVouchers(@RequestParam(value = "ids") String ids) {
        System.out.print(ids);
        String[] voucherIds = ids.split(",");
        List<Voucher> voucherList = new ArrayList<Voucher>();
        Voucher voucher;

        for (int i = 0; i < voucherIds.length; i++) {
            System.out.print(voucherIds[i]);
            voucher = new Voucher();
            voucher.setId(Long.valueOf(voucherIds[i]));
            voucherList.add(voucher);
        }

        int num = iVoucherService.batchDeleteVoucher(voucherList);
        return JSON.toJSONString(num);
    }

    /**
     * 获得Voucher实例
     *
     * @return Voucher实例
     */
    private Voucher getVoucher() {
        Voucher voucher = new Voucher();
        List<Voucher> voucherList = new ArrayList<Voucher>();
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
        return voucher;
    }
}
