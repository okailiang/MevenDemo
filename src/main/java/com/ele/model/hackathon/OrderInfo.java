package com.ele.model.hackathon;

import java.math.BigDecimal;

/**
 * 订单信息
 *
 * @author oukailiang
 * @create 2016-10-11 下午6:15
 */

public class OrderInfo {
    private Integer day_no;//同上
    private Integer minutes;// 同上
    private String order_id;//订单ID
    private String restaurant_id;//餐厅ID
    private Integer deliver_fee;// 总配送费
    private Integer is_online_paid;// 是否在线支付
    private String order_process_minutes;//订单处理时间（分钟)
    private Integer restaurant_num;// 该订单在本餐厅内的第几单
    private String address_type;//收货地址类型，community/office/school/netbar/other
    private Integer is_valid;// 是否有效订单
    private Integer is_book;//是否预定单
    private Integer is_coupon;// 是否使用抵用券
    private Integer is_invoice;// 是否开了发票
    private Integer pindan_flag;// 拼单标志, 1拼单，0非拼单
    private BigDecimal x;// 下单用户所在位置的横坐标
    private BigDecimal y;// 下单用户所在位置的纵坐标
    private String bu_flag_name;//订单所属BU，分高校，白领，早餐，代理商四种
    private Integer eleme_order_total;// 用户实付总价 满减及各类优惠之后的价格）
    private Integer total;// 订单总价(满减及各类优惠之前的总价）
    private Integer cut_money;// 满减优惠金额
    private Integer is_activity;// 订单是否参与活动，1是，0否
    private Integer has_new_user_subsidy;// 是否享用新客补贴，1是，0否
    private Integer hongbao_amount;// 使用红包金额
    private String receiver_deliver_fee;// 收货人实际缴纳的配送费（会员卡减免后的配送费）
    private String user_id;// 用户ID
    private String food_name;// 该订单所涉及主要食物名称（已规范化）
    private String food_category;// 该订单所涉及主要食物分类

    public Integer getDay_no() {
        return day_no;
    }

    public void setDay_no(Integer day_no) {
        this.day_no = day_no;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Integer getDeliver_fee() {
        return deliver_fee;
    }

    public void setDeliver_fee(Integer deliver_fee) {
        this.deliver_fee = deliver_fee;
    }

    public Integer getIs_online_paid() {
        return is_online_paid;
    }

    public void setIs_online_paid(Integer is_online_paid) {
        this.is_online_paid = is_online_paid;
    }

    public String getOrder_process_minutes() {
        return order_process_minutes;
    }

    public void setOrder_process_minutes(String order_process_minutes) {
        this.order_process_minutes = order_process_minutes;
    }

    public Integer getRestaurant_num() {
        return restaurant_num;
    }

    public void setRestaurant_num(Integer restaurant_num) {
        this.restaurant_num = restaurant_num;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public Integer getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(Integer is_valid) {
        this.is_valid = is_valid;
    }

    public Integer getIs_book() {
        return is_book;
    }

    public void setIs_book(Integer is_book) {
        this.is_book = is_book;
    }

    public Integer getIs_coupon() {
        return is_coupon;
    }

    public void setIs_coupon(Integer is_coupon) {
        this.is_coupon = is_coupon;
    }

    public Integer getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(Integer is_invoice) {
        this.is_invoice = is_invoice;
    }

    public Integer getPindan_flag() {
        return pindan_flag;
    }

    public void setPindan_flag(Integer pindan_flag) {
        this.pindan_flag = pindan_flag;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public String getBu_flag_name() {
        return bu_flag_name;
    }

    public void setBu_flag_name(String bu_flag_name) {
        this.bu_flag_name = bu_flag_name;
    }

    public Integer getEleme_order_total() {
        return eleme_order_total;
    }

    public void setEleme_order_total(Integer eleme_order_total) {
        this.eleme_order_total = eleme_order_total;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCut_money() {
        return cut_money;
    }

    public void setCut_money(Integer cut_money) {
        this.cut_money = cut_money;
    }

    public Integer getIs_activity() {
        return is_activity;
    }

    public void setIs_activity(Integer is_activity) {
        this.is_activity = is_activity;
    }

    public Integer getHas_new_user_subsidy() {
        return has_new_user_subsidy;
    }

    public void setHas_new_user_subsidy(Integer has_new_user_subsidy) {
        this.has_new_user_subsidy = has_new_user_subsidy;
    }

    public Integer getHongbao_amount() {
        return hongbao_amount;
    }

    public void setHongbao_amount(Integer hongbao_amount) {
        this.hongbao_amount = hongbao_amount;
    }

    public String getReceiver_deliver_fee() {
        return receiver_deliver_fee;
    }

    public void setReceiver_deliver_fee(String receiver_deliver_fee) {
        this.receiver_deliver_fee = receiver_deliver_fee;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }
}

