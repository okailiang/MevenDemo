package com.ele.demo.handlefile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ele.model.FamilyContractDto;
import com.ele.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author oukailiang
 * @create 2017-06-15 下午7:03
 */

public class HandleContractBank {
    //    private static final String province =
// private static final String city =
    public static final String fileDir = System.getProperty("user.home") + "/Desktop/";
    public static final String IMPORT_FILE = "终极版第二批数据.xlsx";
    private static final List<FamilyContractDto> allContractList = new ArrayList<>();
    public static final Map<String, Long> provinceNameMap = new HashMap<>();
    public static final Map<String, Long> cityNameMap = new HashMap<>();

    //银行名->编号
    private static final Map<String, String> existBankMap = new HashMap<>();
    private static final Map<String, String> errorBankMap = new HashMap<>();

    public static final Map<String, String> errorCityNameMap = new HashMap<>();


    public static void main(String[] args) {
        //处理省市json串
//        parse(province,city);

        initProvinceCity(fileDir + "Sauron目前支持的银行_省城市.xls");
        //初始化银行
        initBankFile(fileDir + "Sauron目前支持的银行_20170613171709.xls");

        //1.导入原始全量数据//
        handleContractFile();


    }

    private static void initProvinceCity(String filePath) {
        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            System.out.println();
            HSSFSheet provinceSheet = workbook.getSheetAt(0);
            HSSFSheet citySheet = workbook.getSheetAt(1);
            HSSFSheet errorCitySheet = workbook.getSheetAt(2);
            for (int rowNum = 1; rowNum <= provinceSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = provinceSheet.getRow(rowNum);
                if (xssfRow != null) {
                    HSSFCell id = xssfRow.getCell(0);
                    HSSFCell name = xssfRow.getCell(1);
                    provinceNameMap.put(getCellValue(name), Long.parseLong(getCellValue(id)));
                }
            }

            for (int rowNum = 1; rowNum <= citySheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = citySheet.getRow(rowNum);
                if (xssfRow != null) {
                    HSSFCell id = xssfRow.getCell(0);
                    HSSFCell name = xssfRow.getCell(1);
                    String cityName = getCellValue(name);

                    cityNameMap.put(cityName, Long.parseLong(getCellValue(id)));
                }
            }


            for (int rowNum = 1; rowNum <= errorCitySheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = errorCitySheet.getRow(rowNum);
                if (xssfRow != null) {
                    HSSFCell errorCityCell = xssfRow.getCell(0);
                    HSSFCell cityCell = xssfRow.getCell(1);
                    String cityName = getCellValue(cityCell);
                    errorCityNameMap.put(getCellValue(errorCityCell), cityName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("省个数 = " + provinceNameMap.size());
        System.out.println("城市个数 = " + cityNameMap.size());
    }

    public static void initBankFile(String filePath) {

        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            System.out.println();
            System.out.println("开始银行名称文件。。。。");
            HSSFSheet xssfSheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    HSSFCell bankName = xssfRow.getCell(1);
                    HSSFCell bankNumber = xssfRow.getCell(2);
                    existBankMap.put(getCellValue(bankName), getCellValue(bankNumber));
                }
            }
            System.out.println("共银行读取记录个数：" + xssfSheet.getLastRowNum());
            //获取代理商名称
            System.out.println("银行个数 = " + existBankMap.size());

            HSSFSheet errorBankSheet = workbook.getSheetAt(1);
            for (int rowNum = 1; rowNum <= errorBankSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = errorBankSheet.getRow(rowNum);
                if (xssfRow != null) {
                    HSSFCell bankName = xssfRow.getCell(0);
                    HSSFCell existBank = xssfRow.getCell(1);
                    errorBankMap.put(getCellValue(bankName), getCellValue(existBank));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束处理代理商文件。。。。");

    }


    private static void parse(String province, String city) {
        String outPath = fileDir + "Sauron目前支持的银行_省城市.xls";
        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook();
            System.out.println();
            HSSFSheet xssfSheet0 = workbook.createSheet("省");
            HSSFSheet xssfSheet = workbook.createSheet("城市");

            JSONArray provincees = JSON.parseArray(province);
            int size0 = provincees.size();
            for (int i = 0; i < size0; i++) {
                JSONObject json = (JSONObject) provincees.get(i);
                Integer id = json.getInteger("id");
                String name = json.getString("name");
                HSSFRow xssRow = xssfSheet0.createRow(i + 1);
                xssRow.createCell(0).setCellValue(id);
                xssRow.createCell(1).setCellValue(name.trim());
            }

            JSONArray provinceCities = JSON.parseArray(city);
            int size = provinceCities.size();
            for (int i = 0; i < size; i++) {
                JSONObject json = (JSONObject) provinceCities.get(i);
                Integer id = json.getInteger("id");
                String name = json.getString("name");
                Integer province_id = json.getInteger("province_id");
                HSSFRow xssRow = xssfSheet.createRow(i + 1);
                xssRow.createCell(0).setCellValue(id);
                xssRow.createCell(1).setCellValue(name.trim());
                xssRow.createCell(2).setCellValue(province_id);
            }


            FileOutputStream fos = new FileOutputStream(new File(outPath));
            workbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, FamilyContractDto> getCityAgentContractMap(List<FamilyContractDto> allContractList) {
        Map<String, FamilyContractDto> agentNameMap = new HashMap<>();
        for (FamilyContractDto familyContractDto : allContractList) {
            agentNameMap.put(familyContractDto.getAgentCityName() + "-" + familyContractDto.getAgentName(), familyContractDto);
        }
        return agentNameMap;
    }

    public static List<FamilyContractDto> handleContractFile(String filePath) {

        XSSFWorkbook workbook;
//        List<FamilyContractDto> contractInfoList = new ArrayList<>();
        Map<String, String> cityAgentMap = new HashMap<>();
        Map<String, String> agentCityMap = new HashMap<>();
        Set<String> replaceAgentSet = new HashSet<>();


        String outModifyAgentFilePath = System.getProperty("user.home") + "/Desktop/合同_替换修改后的代理商_" + DateUtil.defaultFormatMSDate(new Date()) + ".xlsx";
        try {

            File file = new File(filePath);
            InputStream is = new FileInputStream(file);
            workbook = new XSSFWorkbook(is);
            System.out.println();
            System.out.println("开始处理合同文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            CellStyle agentCellStyle = workbook.createCellStyle();
            agentCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            agentCellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    XSSFCell cityNameCell = xssfRow.getCell(4);
                    String cityName = getCellValue(cityNameCell);
                    if (cityName == null || "".equals(cityName.trim())) {
                        cityNameCell = xssfRow.getCell(3);
                        cityName = getCellValue(cityNameCell);
                    }
                    XSSFCell agentNameCell = xssfRow.getCell(5);
                    contractInfo.setAgentCityName(cityName);
                    contractInfo.setAgentName(getCellValue(agentNameCell));

                    cityAgentMap.put(contractInfo.getAgentCityName(), contractInfo.getAgentName());
                    agentCityMap.put(contractInfo.getAgentName(), contractInfo.getAgentCityName());
                    allContractList.add(contractInfo);
                }
            }
            //获取代理商名称
            System.out.println("读取合同个数 = " + allContractList.size());
            System.out.println("代理城市个数 = " + cityAgentMap.keySet().size());
            System.out.println("代理商名称个数 = " + agentCityMap.keySet().size());
            System.out.println("替换代理商个数 = " + replaceAgentSet.size());
            System.out.println();

            //  OutputStream out = new FileOutputStream(new File(outModifyAgentFilePath));
            //workbook.write(out);
            //out.close();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束处理合同文件。。。。");
        return allContractList;
    }

    public static void handleContractFile() {

        XSSFWorkbook workbook;
        String outModifyAgentFilePath = System.getProperty("user.home") + "/Desktop/合同_导入处理结果_银行_城市" + DateUtil.defaultFormatMSDate(new Date()) + ".xlsx";
        Set<String> set = new HashSet<>();
        int count = 0;
        int provinceCount = 0;
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(new File(fileDir + IMPORT_FILE));
            workbook = new XSSFWorkbook(is);
            System.out.println();
            System.out.println("开始处理合同文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell provinceCityCell = xssfRow.getCell(15);

                    String provinceCityName = getCellValue(provinceCityCell);
//
//                    //替换错误银行
//                    if (errorBankMap.containsKey(provinceCityName)) {
//                        count++;
//                        provinceCityCell.setCellStyle(cellStyle);
//                        provinceCityCell.setCellValue(errorBankMap.get(provinceCityName));
//                        set.add(provinceCityName);
//                    }

                    //用于替换错误的城市
//                    if(errorCityNameMap.containsKey(provinceCityName)){
//                        count++;
//                        //provinceCityCell.setCellStyle(cellStyle);
//                        provinceCityCell.setCellValue(errorCityNameMap.get(provinceCityName));
//                        set.add(provinceCityName);
//                    }

//                    //校验替换后的城市
//                    if (!cityNameMap.containsKey(provinceCityName)) {
//                        System.out.println(provinceCityName);
//                        count++;
//                        provinceCityCell.setCellStyle(cellStyle);
//                        provinceCityCell.setCellValue(provinceCityName);
//                        set.add(provinceCityName);
//                    }

                    //校验未替换的城市
                    String[] provinceCity = splitProvinceCity(provinceCityName);
                    String province = provinceCity[0];
                    String city = provinceCity[1];

                    provinceCityCell.setCellValue(province);
                    if (province == null || "".equals(province.trim()) || !provinceNameMap.containsKey(province)) {
                        System.out.println(provinceCityName);
                        provinceCount++;
                        provinceCityCell.setCellValue(provinceCityName);
                        provinceCityCell.setCellStyle(cellStyle);
                    }

                    XSSFCell cityCell = xssfRow.createCell(21);
                    cityCell.setCellValue(city);
                    if (city == null || city.trim().equals("") || !cityNameMap.containsKey(city)) {
                        set.add(city);
                        count++;
                        cityCell.setCellStyle(cellStyle);
                    } else {
                        cityCell.setCellValue(city + "市");
                    }
                }
            }
            OutputStream out = new FileOutputStream(new File(outModifyAgentFilePath));
            workbook.write(out);
            out.close();
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        for (String s : set) {
            sb.append(s).append("\n");
        }
        System.out.println("不支持的城市类数 = " + set.size());
        System.out.println("不支持省的合同数 = " + provinceCount);
        System.out.println("不支持城市的合同数 = " + count);
        System.out.println(sb.toString());
        System.out.println("结束处理合同文件。。。。");
    }

    private static void handleBank(XSSFRow xssfRow){
        if (xssfRow != null) {
            XSSFCell provinceCityCell = xssfRow.getCell(17);

            String provinceCityName = getCellValue(provinceCityCell);
//
        }

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

        return result;
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

            if (result[0].equals("宁夏省")) {
                result[0] = "宁夏回族自治区";
            }
            if (result[0].equals("重庆省")) {
                result[0] = "重庆市";
            }
            if (result[0].equals("天津省")) {
                result[0] = "天津市";
            }
        }

        if (provinceCity.indexOf("上海市") > -1) {
            result = splitProvinceCity(provinceCity, "上海市");
        }
        if (provinceCity.indexOf("北京市") > -1) {
            result = splitProvinceCity(provinceCity, "北京市");
        }
        if (provinceCity.indexOf("重庆市") > -1) {
            result = splitProvinceCity(provinceCity, "重庆市");
        }
        if (provinceCity.indexOf("天津市") > -1) {
            result = splitProvinceCity(provinceCity, "天津市");
        }
        if (provinceCity.indexOf("内蒙古自治区") > -1) {
            result = splitProvinceCity(provinceCity, "内蒙古自治区");
        }
        if (provinceCity.indexOf("新疆维吾尔自治区") > -1) {
            result = splitProvinceCity(provinceCity, "新疆维吾尔自治区");
            //result[0] = "新疆维吾尔自治区";
        }
        if (provinceCity.indexOf("广西壮族自治区") > -1) {
            result = splitProvinceCity(provinceCity, "广西壮族自治区");
            //result[0] = "广西壮族自治区";
        }
        if (provinceCity.indexOf("西藏自治区") > -1) {
            result = splitProvinceCity(provinceCity, "西藏自治区");
            //result[0] = "西藏自治区";
        }
        if (provinceCity.indexOf("宁夏回族自治区") > -1) {
            result = splitProvinceCity(provinceCity, "宁夏回族自治区");
            //result[0] = "宁夏回族自治区";
        }
        if (result[0] == null || result[1] == null || result[0].equals("") || result[1].equals("")) {
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

    private static String getCellValue(XSSFCell cell) {
        return getCellStringValue(cell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
    }

    private static String getCellValue(HSSFCell cell) {
        return getCellStringValue(cell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
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
}
