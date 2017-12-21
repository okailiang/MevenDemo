package com.ele.demo.handlefile;

import com.alibaba.fastjson.JSON;
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

import javax.json.Json;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 处理合同文件
 *
 * @author oukailiang
 * @create 2017-06-07 下午4:29
 */

public class HandleContractFile {
    public static final String fileDir = System.getProperty("user.home") + "/Desktop/";
    public static final String IMPORT_FILE = "合同导入_处理结果_汇总_最终最终版.xlsx";
    //处理后的81
    private static final Map<String, String> noExistAgentMap = new HashMap<>();

    private static final List<FamilyContractDto> perfectContractList = new ArrayList<>();
    private static final List<FamilyContractDto> existAgentList = new ArrayList<>();
    private static final List<FamilyContractDto> allContractList = new ArrayList<>();

    //代理商和城市对应关系
    private static final Map<String, String> allExistCityAgentMap = new HashMap<>();
    private static final Map<String, Long> allExistAgentMap = new HashMap<>();
    //银行名->编号
    private static final Map<String, String> existBankMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        //
        init();

        handelResult(allContractList, perfectContractList, existAgentList);
        //处理合同银行
        handleContractFile();
    }

    private static void init() {
        //String value= "云南七武海\u200B科技有限公司\u200B";value = value.replaceAll("\u200B","");

        //系统中存在的全量数据
        handlePerfectContractFile(fileDir + "全量_代理商和城市关系.xls");
        //合同_系统中存在的代理商.xls
        handleExistAgentFile(fileDir + "全量_代理商.xls");

        //2.处理修改后代理商数据
//        String modify1AgentFile = fileDir + IMPORT_FILE;
//        List<FamilyContractDto> modify1AgentList = handleAgentFile(modify1AgentFile);
//        输出不在系统中的代理商
//        List<FamilyContractDto> noExistAgentList = printNoExistAgent(modify1AgentList, existAgentList);
//        输入到文件中
//        outModify1AgentFile(modify1AgentFile);

        //1.导入原始全量数据
        handleContractFile(fileDir + IMPORT_FILE);

        //系统中存在的城市
//        List<FamilyContractDto> existCityContractList = handleExistCityFile(fileDir + "合同_系统中的城市.xls");
//        printNoExistCityContractList(allContractList, existCityContractList);
        //处理银行
        handleExistBankFile(fileDir + "Sauron目前支持的银行_20170613171709.xls");

    }

    private static void handelResult(List<FamilyContractDto> allContractList, List<FamilyContractDto> perfectContractList, List<FamilyContractDto> existAgentList) {

        //输出不在系统中的代理商
        List<FamilyContractDto> noExistAgentList = printNoExistAgent(allContractList, existAgentList);
        //输出不符合城市和代理商的合同
        List<List<FamilyContractDto>> noCityOrAgentContactList = getNoPerfectAgentContract(allContractList, perfectContractList);
        printCityAgentContract(noCityOrAgentContactList.get(1), "输出代理商不在系统中的合同");
        printCityAgentContract(noCityOrAgentContactList.get(0), "输出代理商在，但城市不在系统中的合同");
        //生成处理结果文件
        outHandleResultContractFile(fileDir + IMPORT_FILE, noCityOrAgentContactList.get(0), noCityOrAgentContactList.get(1));
    }

    private static void printNoExistCityContractList(List<FamilyContractDto> allContractList, List<FamilyContractDto> existCityContractList) {

        Map<String, FamilyContractDto> allCityNameContractMap = getCityNameContractMap(allContractList);
        Map<String, FamilyContractDto> existCityNameContractMap = getCityNameContractMap(existCityContractList);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, FamilyContractDto> entry : allCityNameContractMap.entrySet()) {
            if (existCityNameContractMap.get(entry.getKey()) == null) {
                count++;
                sb.append(entry.getKey()).append("\n");
            }
        }

        System.out.println();
        System.out.println("导入合同中城市总个数：" + allCityNameContractMap.size());
        System.out.println("导入合同中存在系统中的城市总个数：" + existCityNameContractMap.size());
        System.out.println("导入合同中不存在系统中的城市总个数：" + count);
        System.out.println("导入合同中不在系统中的城市名称：\n" + sb.toString());
    }


    /**
     * 输出不在系统中的代理商
     */
    private static List<FamilyContractDto> printNoExistAgent(List<FamilyContractDto> allContractList, List<FamilyContractDto> existAgentList) {

        List<FamilyContractDto> noExistAgentList = new ArrayList<>();
        System.out.println();
        System.out.println("输出导入合同中不在系统中的代理商==============");
        Map<String, FamilyContractDto> allAgentNameMap = getAgentNameMap(allContractList);

        Map<String, FamilyContractDto> existAgentNameMap = getAgentNameMap(existAgentList);

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, FamilyContractDto> entry : allAgentNameMap.entrySet()) {
            if (existAgentNameMap.get(entry.getKey()) == null) {
                count++;
                sb.append("").append(entry.getValue().getAgentName()).append("\n");
                noExistAgentList.add(entry.getValue());
            }
        }
        System.out.println("导入合同中代理商总个数 = " + allAgentNameMap.size());
        System.out.println("系统中存在代理商个数 = " + existAgentNameMap.size());
        System.out.println("导入合同中系统中不存在代理商个数 = " + count);
        System.out.println();
        System.out.println(sb.toString());

        return noExistAgentList;
    }

    /**
     * 输出不符合城市和代理商的合同
     */
    private static List<List<FamilyContractDto>> getNoPerfectAgentContract(List<FamilyContractDto> allContractList, List<FamilyContractDto> perfectContractList) {

        List<FamilyContractDto> noCityContractList = new ArrayList<>();
        List<FamilyContractDto> noAgentContractList = new ArrayList<>();

        System.out.println();
        System.out.println("输出不符合城市和代理商的合同==============");
        Map<String, List<FamilyContractDto>> allAgentNameContractMap = allContractList.stream().collect(Collectors.groupingBy(FamilyContractDto::getAgentName));

        Map<String, List<FamilyContractDto>> perfectAgentNameContractMap = perfectContractList.stream().collect(Collectors.groupingBy(FamilyContractDto::getAgentName));

        for (Map.Entry<String, List<FamilyContractDto>> entry : allAgentNameContractMap.entrySet()) {

            List<FamilyContractDto> existContractList = perfectAgentNameContractMap.get(entry.getKey());
            List<FamilyContractDto> importContractList = entry.getValue();

            if (existContractList != null && !existContractList.isEmpty()) {

                Map<String, FamilyContractDto> existContractMap = getCityNameContractMap(existContractList);

                for (FamilyContractDto contractDto : importContractList) {
                    FamilyContractDto cityNameContractDto = existContractMap.get(contractDto.getAgentCityName());
                    //存在代理商和城市对应关系
                    if (cityNameContractDto == null || !cityNameContractDto.getAgentCityName().equals(contractDto.getAgentCityName())) {
                        noCityContractList.add(contractDto);
                    }
                }
            } else {//代理商不存在
                noAgentContractList.addAll(importContractList);
            }
        }

        System.out.println("导入合同总个数 = " + allContractList.size());
        System.out.println("导入不符合城市和代理商合同总个数 = " + (noCityContractList.size() + noAgentContractList.size()));
        System.out.println("导入不符合代理商合同总个数 = " + noAgentContractList.size());
        System.out.println("不符合城市的合同个数 = " + noCityContractList.size());
        //
        return Arrays.asList(noCityContractList, noAgentContractList);
    }

    private static void printCityAgentContract(List<FamilyContractDto> cityAgentContractList, String title) {
        StringBuilder sb = new StringBuilder();
        for (FamilyContractDto contractDto : cityAgentContractList) {
            sb.append("").append(contractDto.getAgentCityName()).append("");
            sb.append("  |  ");
            sb.append("").append(contractDto.getAgentName()).append("");
            sb.append("\n");
        }
        System.out.println();
        System.out.println(title + "=========================");
        System.out.println(sb.toString());
    }

    private static Map<String, FamilyContractDto> getAgentNameMap(List<FamilyContractDto> allContractList) {
        Map<String, FamilyContractDto> agentNameMap = new HashMap<>();
        for (FamilyContractDto familyContractDto : allContractList) {
            agentNameMap.put(familyContractDto.getAgentName(), familyContractDto);
        }
        return agentNameMap;
    }

    private static Map<String, FamilyContractDto> getCityNameContractMap(List<FamilyContractDto> allContractList) {
        Map<String, FamilyContractDto> agentNameMap = new HashMap<>();
        for (FamilyContractDto familyContractDto : allContractList) {
            agentNameMap.put(familyContractDto.getAgentCityName(), familyContractDto);
        }
        return agentNameMap;
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

                    //替换修改后的代理商
                    if (noExistAgentMap.containsKey(contractInfo.getAgentName())) {
                        agentNameCell.setCellValue(noExistAgentMap.get(contractInfo.getAgentName()));
                        agentNameCell.setCellStyle(agentCellStyle);
                        replaceAgentSet.add(contractInfo.getAgentName());
                    }
                }
            }
            //获取代理商名称
            System.out.println("读取合同个数 = " + allContractList.size());
            System.out.println("代理城市个数 = " + cityAgentMap.keySet().size());
            System.out.println("代理商名称个数 = " + agentCityMap.keySet().size());
            System.out.println("替换代理商个数 = " + replaceAgentSet.size());
            System.out.println();
            //打印代理商名称
            //printAgentName(agentCityMap);
            //打印城市名称
            //printCityName(cityAgentMap);
            //打印城市和代理商
            //printCityAgentName(cityAgentMap);
            //printCityAgentName(contractInfoList);

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
        String outModifyAgentFilePath = System.getProperty("user.home") + "/Desktop/合同_导入处理结果_" + DateUtil.defaultFormatMSDate(new Date()) + ".xlsx";
        Set<String> set = new HashSet<>();
        int count = 0;
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(new File(fileDir + IMPORT_FILE));
            workbook = new XSSFWorkbook(is);
            System.out.println();
            System.out.println("开始处理合同文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    XSSFCell bankNameCell = xssfRow.getCell(17);
//                    XSSFCell cityNameCell = xssfRow.getCell(16);

//                    String[] bank = splitBankBranchName(getCellValue(bankNameCell));
//                    String bankName = bank[0];
//                    String branchBank = bank[1];
//
//                    if (bankName != null && !"".equals(bankName.trim())) {
//                        bankNameCell.setCellValue(bankName);
//                    }

                    String bankName = getCellValue(bankNameCell);
                    if (!existBankMap.containsKey(bankName)) {
                        count++;
                        set.add(bankName);
                        bankNameCell.setCellStyle(cellStyle);
                    }

//                    XSSFCell branchBankCell = xssfRow.createCell(20);
//                    branchBankCell.setCellValue(branchBank);
//                    if (branchBank == null || branchBank.trim().equals("")) {
//                        branchBankCell.setCellStyle(cellStyle);
//                        System.out.println("无支行 =" + rowNum);
//                    }
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
        System.out.println("不支持的银行类数 = " + set.size());
        System.out.println("银行不支持的合同数 = " + count);
        System.out.println(sb.toString());
        System.out.println("结束处理合同文件。。。。");
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

    private static String getCellValue(XSSFCell cell) {
        return getCellStringValue(cell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
    }

    private static String getCellValue(HSSFCell cell) {
        return getCellStringValue(cell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
    }

    public static List<FamilyContractDto> handleAgentFile(String filePath) {

        XSSFWorkbook workbook;
        List<FamilyContractDto> contractInfoList = new ArrayList<>();
        Map<String, String> agentCityMap = new HashMap<>();
        try {
            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            System.out.println();
            System.out.println("开始处理代理商文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(2);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    XSSFCell agentName = xssfRow.getCell(1);
                    contractInfo.setAgentName(getCellStringValue(agentName).trim().replaceAll("\u200B", "").replaceAll(" ", ""));
                    if (agentCityMap.get(contractInfo.getAgentName()) != null) {
                        System.out.println(contractInfo.getAgentName());
                    }

                    XSSFCell noExistAgentNameCell = xssfRow.getCell(0);
                    String noExistAgentName = getCellStringValue(noExistAgentNameCell).trim().replaceAll("\u200B", "").replaceAll(" ", "");
                    noExistAgentMap.put(noExistAgentName, contractInfo.getAgentName());


                    agentCityMap.put(contractInfo.getAgentName(), "");
                    contractInfoList.add(contractInfo);
                }
            }
            System.out.println("读取代理商记录个数：" + contractInfoList.size());
            //获取代理商名称
            System.out.println("代理商名称个数 = " + agentCityMap.keySet().size());
            //打印代理商名称
            //printAgentName(agentCityMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束处理代理商文件。。。。");
        return contractInfoList;
    }

    public static void handleExistBankFile(String filePath) {

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
            System.out.println("共读取记录个数：" + xssfSheet.getLastRowNum());
            //获取代理商名称
            System.out.println("银行个数 = " + existBankMap.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束处理代理商文件。。。。");

    }


    /**
     * 获得满足条件的合同
     *
     * @param filePath
     * @return
     */
    public static List<FamilyContractDto> handlePerfectContractFile(String filePath) {

        HSSFWorkbook workbook;
//        List<FamilyContractDto> contractInfoList = new ArrayList<>();
//        Map<String, String> cityAgentMap = new HashMap<>();
        Map<String, String> agentCityMap = new HashMap<>();
        List<String> repeatCityList = new ArrayList<>();
        System.out.println();
        System.out.println("开始处理满足条件代理商和城市关系文件。。。");
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            HSSFSheet xssfSheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    contractInfo.setAgentCityId(Long.parseLong(getCellStringValue(xssfRow.getCell(0))));
                    contractInfo.setAgentCityName(getCellValue(xssfRow.getCell(1)).trim());

                    contractInfo.setAgentId(Long.parseLong(getCellStringValue(xssfRow.getCell(2))));
                    contractInfo.setAgentName(getCellValue(xssfRow.getCell(3)).trim());


                    if (allExistCityAgentMap.get(contractInfo.getAgentCityName()) != null) {
                        repeatCityList.add(contractInfo.getAgentCityName() + " | " + contractInfo.getAgentName());
                    }
                    allExistCityAgentMap.put(contractInfo.getAgentCityName(), contractInfo.getAgentName());

                    agentCityMap.put(contractInfo.getAgentName(), contractInfo.getAgentCityName());
                    perfectContractList.add(contractInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("一共处理条件代理商和城市关系的个数：" + perfectContractList.size());
        System.out.println();
        //获取代理商名称
        System.out.println("代理城市个数 = " + allExistCityAgentMap.keySet().size());
        System.out.println("代理商名称个数 = " + agentCityMap.keySet().size());
        //
        System.out.println("重复城市的代理商个数 = " + repeatCityList.size());
        System.out.println("重复城市的代理商======================");
        System.out.println();
        System.out.println(JSON.toJSONString(repeatCityList));
        System.out.println();
        System.out.println("结束处理满足条件代理商和城市关系文件。。。");

        return perfectContractList;
    }

    /**
     * 获得满足条件的合同
     *
     * @param filePath
     * @return
     */
    public static List<FamilyContractDto> handleExistAgentFile(String filePath) {

        HSSFWorkbook workbook;
        System.out.println();
        System.out.println("开始处理已经存在的代理商。。。");
//        List<FamilyContractDto> contractInfoList = new ArrayList<>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            HSSFSheet xssfSheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    contractInfo.setAgentId(Long.parseLong(getCellStringValue(xssfRow.getCell(0))));
                    contractInfo.setAgentName(getCellValue(xssfRow.getCell(1)).trim());
                    //contractInfo.setId(Long.parseLong(getCellStringValue(xssfRow.getCell(2))));

                    allExistAgentMap.put(contractInfo.getAgentName(), contractInfo.getAgentId());
                    existAgentList.add(contractInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("一共处理已经存在的代理商个数：" + existAgentList.size());
        System.out.println("结束处理已经存在的代理商。。。");
        return existAgentList;
    }

    /**
     * 获得已经在系统中的城市
     *
     * @param filePath
     * @return
     */
    public static List<FamilyContractDto> handleExistCityFile(String filePath) {

        HSSFWorkbook workbook;
        System.out.println();
        System.out.println("开始处理已经存在的城市。。。");
        List<FamilyContractDto> contractInfoList = new ArrayList<>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            HSSFSheet xssfSheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    contractInfo.setAgentCityId(Long.parseLong(getCellStringValue(xssfRow.getCell(0))));
                    contractInfo.setAgentCityName(getCellStringValue(xssfRow.getCell(1)).trim());
                    contractInfoList.add(contractInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("一共处理已经存在的城市个数：" + contractInfoList.size());
        System.out.println("结束处理已经存在的城市。。。");
        return contractInfoList;
    }

    /**
     * 输出结果文件
     *
     * @param filePath
     * @param noCityContactList
     * @param noAgentContactList
     */
    public static void outHandleResultContractFile(String filePath, List<FamilyContractDto> noCityContactList, List<FamilyContractDto> noAgentContactList) {

        XSSFWorkbook workbook;
        String outFilePath = System.getProperty("user.home") + "/Desktop/合同_导入处理结果_" + DateUtil.defaultFormatMSDate(new Date()) + ".xlsx";
        int noAgentCount = 0;
        int noCityCount = 0;
        Set<String> cityAgentNameKeySet = new HashSet<>();
        try {

            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //标记城市
            CellStyle cityCellStyle = workbook.createCellStyle();
            cityCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cityCellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
            //标记代理商
            CellStyle agentCellStyle = workbook.createCellStyle();
            agentCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            agentCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            //
            Map<String, FamilyContractDto> noCityContractMap = getCityAgentContractMap(noCityContactList);
            Map<String, FamilyContractDto> noAgentContractMap = getCityAgentContractMap(noAgentContactList);

            System.out.println();
            System.out.println("开始生成处理结果合同文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
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

                    String cityAgentNameKey = contractInfo.getAgentCityName() + "-" + contractInfo.getAgentName();
                    cityAgentNameKeySet.add(cityAgentNameKey);
                    //
                    FamilyContractDto noCityContract = noCityContractMap.get(cityAgentNameKey);
                    FamilyContractDto noAgentContract = noAgentContractMap.get(cityAgentNameKey);

                    if (noCityContract != null && noCityContract.getAgentName().equals(contractInfo.getAgentName())) {
                        noCityCount++;
                        cityNameCell.setCellValue(contractInfo.getAgentCityName());
                        cityNameCell.setCellStyle(cityCellStyle);
                    }
                    if (noAgentContract != null && noAgentContract.getAgentName().equals(contractInfo.getAgentName())) {
                        noAgentCount++;
                        agentNameCell.setCellValue(contractInfo.getAgentName());
                        agentNameCell.setCellStyle(agentCellStyle);
                        cityNameCell.setCellStyle(agentCellStyle);
                    }
                }
            }

            //输出
            OutputStream out = new FileOutputStream(new File(outFilePath));
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("导入合同中城市和代理商key个数 = " + cityAgentNameKeySet.size());
        System.out.println("系统中没有代理商的合同文件个数 = " + noAgentCount);
        System.out.println("系统中有代理商但城市不对的合同文件个数 = " + noCityCount);
        System.out.println("结束生成处理结果合同文件。。。。");
    }

    /**
     * 输出结果文件
     *
     * @param filePath
     */
    public static void outModify1AgentFile(String filePath) {

        XSSFWorkbook workbook;
        String outFilePath = System.getProperty("user.home") + "/Desktop/修改后的代理商" + DateUtil.defaultFormatMSDate(new Date()) + ".xlsx";
        int noAgentCount = 0;
        try {

            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //标记城市
            CellStyle cityCellStyle = workbook.createCellStyle();
            cityCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cityCellStyle.setFillForegroundColor(HSSFColor.LIME.index);
            //标记代理商
            CellStyle agentCellStyle = workbook.createCellStyle();
            agentCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            agentCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            //

            System.out.println();
            System.out.println("开始修改后的代理商处理文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(2);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    XSSFCell agentName = xssfRow.getCell(1);
                    contractInfo.setAgentName(agentName.toString().trim().replaceAll("\u200B", "").replaceAll(" ", ""));
                    if (!allExistAgentMap.containsKey(contractInfo.getAgentName())) {
                        noAgentCount++;
                        agentName.setCellValue(contractInfo.getAgentName());
                        agentName.setCellStyle(agentCellStyle);
                    }
                }
            }

            //输出
            OutputStream out = new FileOutputStream(new File(outFilePath));
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("修改后的代理商仍不符合的个数 = " + noAgentCount);
        System.out.println("结束修改后的代理商处理文件。。。。");
    }

    private static void printAgentName(Map<String, String> agentCityMap) {
        StringBuilder sb = new StringBuilder();
        for (String agentName : agentCityMap.keySet()) {
            sb.append("'").append(agentName.trim()).append("',");
        }
        System.out.println();
        System.out.println(sb.toString());
    }

    private static void printCityName(Map<String, String> cityAgentMap) {
        StringBuilder sb = new StringBuilder();
        for (String cityName : cityAgentMap.keySet()) {
            sb.append("'").append(cityName.trim()).append("',");
        }
        System.out.println();
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

    private static void printCityAgentName(List<FamilyContractDto> contractInfoList) {
        StringBuilder sb = new StringBuilder();
        int size = contractInfoList.size();
        int index = 0;
        for (FamilyContractDto contractInfo : contractInfoList) {
            index++;
            //城市
            sb.append("(s.city_name ='").append(contractInfo.getAgentCityName().trim()).append("'");
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

}
