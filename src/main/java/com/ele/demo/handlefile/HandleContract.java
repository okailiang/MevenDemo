package com.ele.demo.handlefile;

import com.ele.model.FamilyContractDto;
import com.ele.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author oukailiang
 * @create 2017-06-06 下午3:58
 */

public class HandleContract {
    public static final String fileDir = System.getProperty("user.home") + "/Desktop/";
    public static final String IMPORT_FILE = "合同导入_处理结果_汇总_最终最终版.xlsx";


    public static void main(String[] args) throws IOException {
        // System.out.println(System.getProperties());
//        handleContractFile(System.getProperty("user.home") + "/Desktop/合同导入模版.xlsx");
        InputStream is = new FileInputStream(new File(fileDir + IMPORT_FILE));
        handleContractFile(is);
    }

    public static void handleContractFile(InputStream is) {

        XSSFWorkbook workbook;
        List<FamilyContractDto> contractInfoList = new ArrayList<>();
        Map<String, String> cityAgentMap = new HashMap<>();
        Map<String, String> agentCityMap = new HashMap<>();
        int index = 0;
        try {
            workbook = new XSSFWorkbook(is);
            System.out.println("处理数据开始。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                index++;
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractDto = new FamilyContractDto();

                    setContractDto(contractDto, xssfRow);

                    contractInfoList.add(contractDto);
                }
            }
            System.out.println("处理数据结束。。。。");
        } catch (Exception e) {
            System.out.println("错误行数 =" + index);
            e.printStackTrace();
        }
    }

    private static void setContractDto(FamilyContractDto contract, XSSFRow xssfRow) throws Exception {

        if (!"合作合同".equals(getCellStringValue(xssfRow.getCell(0)))) {
            System.out.println("合同类型不是合作合同，合同编号： " + getCellStringValue(xssfRow.getCell(1)));
        }
        contract.setContractType(1);
        contract.setContractCode(getCellStringValue(xssfRow.getCell(1)).trim());

        String cityName = getCellValue(xssfRow.getCell(4));
        if (cityName == null || "".equals(cityName)) {
            cityName = getCellValue(xssfRow.getCell(3));
        }
        contract.setAgentCityName(cityName);
        contract.setAgentName(getCellValue(xssfRow.getCell(5)));

        contract.setBeginTime(DateUtil.strToDateTime(getCellStringValue(xssfRow.getCell(6))));
        contract.setEndTime(DateUtil.strToDateTime(getCellStringValue(xssfRow.getCell(7))));
        contract.setSignTime(DateUtil.strToDateTime(getCellStringValue(xssfRow.getCell(8))));

        contract.setShareRatio(formatDouble(xssfRow.getCell(9).toString()));
        contract.setAccountLimit(Double.parseDouble(getCellStringValue(xssfRow.getCell(10))));
        contract.setReturnRation(formatDouble(getCellStringValue(xssfRow.getCell(11))));

        contract.setPublicAccount(getCellStringValue(xssfRow.getCell(12)).trim());
        //1:公司账户
        if (!"公司账户".equals(getCellStringValue(xssfRow.getCell(13)))) {
            System.out.println("公司账户类型不同， 合同编号： " + getCellStringValue(xssfRow.getCell(1)));
        }
        contract.setAccountType(1);
        contract.setBankNumbers(getCellValue(xssfRow.getCell(14)));
        contract.setBranchBankProvince(getCellValue(xssfRow.getCell(15)));
        contract.setBranchBankCity(getCellValue(xssfRow.getCell(16)));
        contract.setBankName(getCellValue(xssfRow.getCell(17)));
        contract.setBranchBankName(getCellValue(xssfRow.getCell(18)));

        contract.setDeposit(new BigDecimal(getCellStringValue(xssfRow.getCell(19)).trim()));
        contract.setTransferFee(new BigDecimal(getCellStringValue(xssfRow.getCell(20)).trim()));

    }


    private static Double formatDouble(String value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(value));
        return bigDecimal.multiply(BigDecimal.valueOf(100)).doubleValue();
    }

    private static void printProvinceCity(String provinceCity) {
        StringBuilder sb = new StringBuilder();
        if (provinceCity.indexOf("省") < 0 && provinceCity.indexOf("上海市") < 0 && provinceCity.indexOf("北京市") < 0
                && provinceCity.indexOf("内蒙古自治区") < 0 && provinceCity.indexOf("新疆") < 0
                && provinceCity.indexOf("广西") < 0 && provinceCity.indexOf("西藏") < 0) {
            sb.append(provinceCity);
        }
        //江西  湖北  辽宁 广东 江苏
//        if(provinceCity.indexOf("广东") > -1){
//            sb.append(provinceCity);
//        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }

    }

    private static void printBankBranchName(String bankBranchName) {
        StringBuilder sb = new StringBuilder();
        if (bankBranchName.indexOf("银行") < 0) {
            sb.append(" | " + bankBranchName);
        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }
    }

    private static String getCellValue(XSSFCell cell) {
        return getCellStringValue(cell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
    }

    private static String getCellValue(HSSFCell cell) {
        return getCellStringValue(cell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
    }

    /**
     * 分割省市
     *
     * @param provinceCity
     * @return
     */
    private static String[] splitProvinceCity(String provinceCity) {
        String[] result = new String[2];
        int index;
        if ((index = provinceCity.indexOf("省")) > -1) {
            result[0] = provinceCity.substring(0, index + 1);
            result[1] = provinceCity.substring(index + 1, provinceCity.length());
        }
        if (provinceCity.indexOf("上海市") > -1) {
            result = splitProvinceCity(provinceCity, "上海市");
        }
        if (provinceCity.indexOf("北京市") > -1) {
            result = splitProvinceCity(provinceCity, "北京市");
        }
        if (provinceCity.indexOf("内蒙古自治区") > -1) {
            result = splitProvinceCity(provinceCity, "内蒙古自治区");
        }
        if (provinceCity.indexOf("新疆") > -1) {
            result = splitProvinceCity(provinceCity, "新疆");
        }
        if (provinceCity.indexOf("广西") > -1) {
            result = splitProvinceCity(provinceCity, "广西");
        }
        if (provinceCity.indexOf("西藏") > -1) {
            result = splitProvinceCity(provinceCity, "西藏");
        }
        if (result[0] == null || result[1] == null || result[0] == "" || result[1] == "") {
            System.out.println(provinceCity);
        }
        return result;
    }

    private static String[] splitProvinceCity(String provinceCity, String splitKey) {
        String[] result = new String[2];
        result[0] = splitKey;
        result[1] = provinceCity.substring(splitKey.length(), provinceCity.length());
        return result;
    }

    /**
     * 分割银行和支行
     *
     * @param bankBranchName
     * @return
     */
    private static String[] splitBankBranchName(String bankBranchName) {
        String[] result = new String[2];
        int index = bankBranchName.indexOf("银行");
        if (index > -1) {
            result[0] = bankBranchName.substring(0, index + 2);
            result[1] = bankBranchName.substring(index + 2, bankBranchName.length());
            result[1] = result[1].replaceAll("股份有限公司", "");
        }
        //打印非大型银行
        printNoBigBank(result);

        return result;
    }

    private static void printNoBigBank(String[] result) {
        //打印非大型银行
        List<String> bigBankList = Arrays.asList("中国工商银行", "中国建设银行", "招商银行", "中国农业银行", "中国银行", "南京银行",
                "中国邮政储蓄银行", "广发银行", "交通银行", "中国民生银行", "平安银行", "兴业银行", "中信银行", "华夏银行",
                "中国光大银行", "浙江泰隆商业银行", "徽商银行", "杭州银行", "上海银行", "天津银行", "北京银行", "江苏银行",
                "上海浦东发展银行", "浦发银行", "上海农商银行", "上海农村商业银行", "杭州联合农村商业银行");
        if (!bigBankList.contains(result[0])) {
            System.out.println(result[0] + " | " + result[1]);
        }
    }

    public static void handleContractFile(String filePath) {

        XSSFWorkbook workbook;
        List<ContractInfo> contractInfoList = new ArrayList<>();
        Map<String, String> cityAgentMap = new HashMap<>();
        Map<String, String> agentCityMap = new HashMap<>();
        try {

            File file = new File(filePath);
            InputStream is = new FileInputStream(file);
            workbook = new XSSFWorkbook(is);

            // Read the Sheet
            //for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            if (xssfSheet == null) {
                //continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    ContractInfo contractInfo = new ContractInfo();
                    XSSFCell cityName = xssfRow.getCell(3);
                    XSSFCell agentName = xssfRow.getCell(4);
                    contractInfo.setCityName(getCellStringValue(cityName));
                    contractInfo.setAgentName(getCellStringValue(agentName));

                    cityAgentMap.put(contractInfo.getCityName(), contractInfo.getAgentName());
                    agentCityMap.put(contractInfo.getAgentName(), contractInfo.getCityName());
                    contractInfoList.add(contractInfo);
                }
            }

            //获取代理商名称
            System.out.println("代理城市个数 = " + cityAgentMap.keySet().size());
            System.out.println("代理商名称个数 = " + agentCityMap.keySet().size());
            //打印代理商名称
            //printAgentName(agentCityMap);
            //打印城市名称
//            printCityName(cityAgentMap);
            //打印城市和代理商
            //printCityAgentName(cityAgentMap);
            printCityAgentName(contractInfoList);

//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printAgentName(Map<String, String> agentCityMap) {
        StringBuilder sb = new StringBuilder();
        for (String agentName : agentCityMap.keySet()) {
            sb.append("'").append(agentName.trim()).append("',");
        }
        System.out.println(sb.toString());
    }

    private static void printCityName(Map<String, String> cityAgentMap) {
        StringBuilder sb = new StringBuilder();
        for (String cityName : cityAgentMap.keySet()) {
            sb.append("'").append(cityName.trim()).append("',");
        }
        System.out.println(sb.toString());
    }

    private static void printCityAgentName(Map<String, String> agentCityMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> cityAgent : agentCityMap.entrySet()) {
            //城市
            sb.append("(s.city_name ='").append(cityAgent.getValue().trim()).append("'");
            //代理商
            sb.append(" and ac.agent_name ='").append(cityAgent.getValue().trim()).append("') or ");
        }
        System.out.println(sb.toString());
    }

    private static void printCityAgentName(List<ContractInfo> contractInfoList) {
        StringBuilder sb = new StringBuilder();
        int size = contractInfoList.size();
        int index = 0;
        for (ContractInfo contractInfo : contractInfoList) {
            index++;
            //城市
            sb.append("(s.city_name ='").append(contractInfo.getCityName().trim()).append("'");
            //代理商
            sb.append(" and ac.agent_name ='").append(contractInfo.getAgentName().trim()).append("') or ");
            if (index == size / 2) {
                System.out.println(sb.toString());
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                System.out.println("======================");
                sb = new StringBuilder();
            }
        }
        System.out.println(sb.toString());
    }

    /**
     * 获取Excel单元格中得数据，作为字符串
     *
     * @param cell
     * @return
     */
    public static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        Integer type = cell.getCellType();
        if (type == Cell.CELL_TYPE_BLANK) {
            return "";
        } else if (type == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (type == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
            } else {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                return nf.format(cell.getNumericCellValue());
            }
        } else if (type == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (type == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        }
        return null;
    }


    static class ContractInfo {
        private String cityName;
        private String agentName;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }
    }
}
