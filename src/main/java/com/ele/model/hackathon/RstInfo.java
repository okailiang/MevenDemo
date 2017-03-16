package com.ele.model.hackathon;

import java.math.BigDecimal;

/**
 * 餐馆信息
 *
 * @author oukailiang
 * @create 2016-10-11 下午6:24
 */

public class RstInfo {
    private String restaurant_id;            //餐厅ID
    private String primary_category;         //餐厅所属分类
    private String food_name_list;          //该餐厅里top10品种最多的食物种类
    private String category_list;            //该餐厅top5品种最多的食物大类
    private BigDecimal x;                       // 餐厅所在位置的横坐标
    private BigDecimal y;                       // 餐厅所在位置的纵坐标
    private Integer agent_fee;               // 配送费
    private Integer is_premium;              // 是否品牌馆，1是，0否
    private String address_type;             //餐厅地址类型，同上，
    private Double good_rating_rate;         //4星以上好评占比
    private Integer open_month_num;
    private Integer has_image;                //餐厅是否有图片，1是，0否
    private Integer has_food_img;             //食物是否有图片
    private Integer min_deliver_amount;       //最低起送价
    private Integer time_ensure_spent;        //保证多久可以送达，is_time_ensure=1时可用
    private Integer is_time_ensure;           //是否有时间保证
    private Integer is_ka;                    //是否是连锁店大大客户
    private Integer is_time_ensure_discount;  //是否有超时折扣
    private Integer is_eleme_deliver;         //是否是饿了么配送
    private Double radius;
    private String bu_flag;                  //餐厅所属bu
    private String brand_name;               //餐厅所属品牌，只有部分餐厅有，如肯德基，麦当劳等
    private Double service_rating;           //服务综合评价，5分制
    private Integer invoice;                  //是否支持开发票
    private Integer online_payment;           //是否在线支付
    private Integer public_degree;           //信息公开等级，餐厅厨房，大堂，门面等的公开级别0-3，公开的信息越大，数字越大
    private Integer food_num;                 //餐厅内食物品种数量
    private Integer food_image_num;           //有图片的食物品种数量
    private Integer is_promotion_info;        //是否有促销信息
    private Integer is_bookable;              //是否支持预定单

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getPrimary_category() {
        return primary_category;
    }

    public void setPrimary_category(String primary_category) {
        this.primary_category = primary_category;
    }

    public String getFood_name_list() {
        return food_name_list;
    }

    public void setFood_name_list(String food_name_list) {
        this.food_name_list = food_name_list;
    }

    public String getCategory_list() {
        return category_list;
    }

    public void setCategory_list(String category_list) {
        this.category_list = category_list;
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

    public Integer getAgent_fee() {
        return agent_fee;
    }

    public void setAgent_fee(Integer agent_fee) {
        this.agent_fee = agent_fee;
    }

    public Integer getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(Integer is_premium) {
        this.is_premium = is_premium;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public Double getGood_rating_rate() {
        return good_rating_rate;
    }

    public void setGood_rating_rate(Double good_rating_rate) {
        this.good_rating_rate = good_rating_rate;
    }

    public Integer getOpen_month_num() {
        return open_month_num;
    }

    public void setOpen_month_num(Integer open_month_num) {
        this.open_month_num = open_month_num;
    }

    public Integer getHas_image() {
        return has_image;
    }

    public void setHas_image(Integer has_image) {
        this.has_image = has_image;
    }

    public Integer getHas_food_img() {
        return has_food_img;
    }

    public void setHas_food_img(Integer has_food_img) {
        this.has_food_img = has_food_img;
    }

    public Integer getMin_deliver_amount() {
        return min_deliver_amount;
    }

    public void setMin_deliver_amount(Integer min_deliver_amount) {
        this.min_deliver_amount = min_deliver_amount;
    }

    public Integer getTime_ensure_spent() {
        return time_ensure_spent;
    }

    public void setTime_ensure_spent(Integer time_ensure_spent) {
        this.time_ensure_spent = time_ensure_spent;
    }

    public Integer getIs_time_ensure() {
        return is_time_ensure;
    }

    public void setIs_time_ensure(Integer is_time_ensure) {
        this.is_time_ensure = is_time_ensure;
    }

    public Integer getIs_ka() {
        return is_ka;
    }

    public void setIs_ka(Integer is_ka) {
        this.is_ka = is_ka;
    }

    public Integer getIs_time_ensure_discount() {
        return is_time_ensure_discount;
    }

    public void setIs_time_ensure_discount(Integer is_time_ensure_discount) {
        this.is_time_ensure_discount = is_time_ensure_discount;
    }

    public Integer getIs_eleme_deliver() {
        return is_eleme_deliver;
    }

    public void setIs_eleme_deliver(Integer is_eleme_deliver) {
        this.is_eleme_deliver = is_eleme_deliver;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public String getBu_flag() {
        return bu_flag;
    }

    public void setBu_flag(String bu_flag) {
        this.bu_flag = bu_flag;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public Double getService_rating() {
        return service_rating;
    }

    public void setService_rating(Double service_rating) {
        this.service_rating = service_rating;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Integer getOnline_payment() {
        return online_payment;
    }

    public void setOnline_payment(Integer online_payment) {
        this.online_payment = online_payment;
    }

    public Integer getPublic_degree() {
        return public_degree;
    }

    public void setPublic_degree(Integer public_degree) {
        this.public_degree = public_degree;
    }

    public Integer getFood_num() {
        return food_num;
    }

    public void setFood_num(Integer food_num) {
        this.food_num = food_num;
    }

    public Integer getFood_image_num() {
        return food_image_num;
    }

    public void setFood_image_num(Integer food_image_num) {
        this.food_image_num = food_image_num;
    }

    public Integer getIs_promotion_info() {
        return is_promotion_info;
    }

    public void setIs_promotion_info(Integer is_promotion_info) {
        this.is_promotion_info = is_promotion_info;
    }

    public Integer getIs_bookable() {
        return is_bookable;
    }

    public void setIs_bookable(Integer is_bookable) {
        this.is_bookable = is_bookable;
    }
}
