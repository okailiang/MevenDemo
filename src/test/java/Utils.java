/**
 * Created by oukailiang on 16/7/20.
 */
public class Utils {
    public static void doInsertSql(String sql){
        String insertSql = "";
        StringBuffer sb = new StringBuffer();
        String[] sqls = sql.split(",");
        System.out.print(sqls.length);
        for(int i = 0;i < sqls.length;i++){
            sb.append("html += \"<td>\" + data[i].").append(sqls[i]).append("+ \"</td>\";\n");
        }
        System.out.print(sb.toString());
    }
    public static  void main(String[] args){
        String sql1= "id,voucher_no,amount,active_id,consumer_type,active_name," +
                "      active_type,status,voucher_begin_time,voucher_end_time,active_start_time," +
                "      active_end_time,active_image_url,active_rule,full,type,use_times,version," +
                "      create_time,last_modified,modified_by,business_type,phone_num,city_id,strategy_count";

        String sql = "id," +
                "voucherNo," +
                "amount," +
                "activeId,batchNo,consumerType,activeName,activeType,status,voucherBeginTime,voucherEndTime,activeStartTime,activeEndTime,activeImageUrl,activeRule,full,type,useTimes,version,createTime,lastModified,modifiedBy,businessType,phoneNum,cityId,strategyCount" ;
        doInsertSql(sql);
    }
}
