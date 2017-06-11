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

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 处理合同文件
 *
 * @author oukailiang
 * @create 2017-06-07 下午4:29
 */

public class HandleContractFile {
    //处理后的81
    private static final Map<String, String> noExistAgentMap = new HashMap<>();

    //代理商和城市对应关系
    private static final Map<String, String> allExistCityAgentMap = new HashMap<>();
    private static final Map<String, Long> allExistAgentMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        // System.out.println(System.getProperties());

        //String value= "云南七武海\u200B科技有限公司\u200B";
        //value = value.replaceAll("\u200B","");
        //System.out.println(value);
        String fileDir = System.getProperty("user.home") + "/Desktop/";

        //系统中存在的全量数据
        List<FamilyContractDto> perfectContractList = handlePerfectContractFile(fileDir + "全量_代理商和城市关系.xls");
        //合同_系统中存在的代理商.xls
        List<FamilyContractDto> existAgentList = handleExistAgentFile(fileDir + "全量_代理商.xls");

        //2.处理修改后代理商数据
        String modify1AgentFile = fileDir + "合同_导入处理结果_反馈2.xlsx";
        List<FamilyContractDto> modify1AgentList = handleAgentFile(modify1AgentFile);
        //输出不在系统中的代理商
        //List<FamilyContractDto> noExistAgentList = printNoExistAgent(modify1AgentList, existAgentList);
        //输入到文件中
        //outModify1AgentFile(modify1AgentFile);

        //1.导入原始全量数据
        List<FamilyContractDto> allContractList = handleContractFile(fileDir + "合同_导入处理结果_反馈2.xlsx");

        //系统中存在的城市
        //List<FamilyContractDto> existCityContractList = handleExistCityFile(fileDir + "合同_系统中的城市.xls");
//        printNoExistCityContractList(allContractList, existCityContractList);

        //1.allContractList   2.modify1AgentList
        handelResult(allContractList, perfectContractList, existAgentList);
    }

    private static void handelResult(List<FamilyContractDto> allContractList, List<FamilyContractDto> perfectContractList, List<FamilyContractDto> existAgentList) {

        //输出不在系统中的代理商
        List<FamilyContractDto> noExistAgentList = printNoExistAgent(allContractList, existAgentList);
        //输出不符合城市和代理商的合同
        List<FamilyContractDto> noPerfectAgentContracts = printNoPerfectAgentContract(allContractList, perfectContractList);
        //输出代理商不在系统中的合同和代理商在而城市不在
        List<List<FamilyContractDto>> noCityOrAgentContactList = printNoAgentContract(noExistAgentList, noPerfectAgentContracts);
        //生成处理结果文件
        String fileDir = System.getProperty("user.home") + "/Desktop/合同_导入处理结果_反馈2.xlsx";
        //outHandleResultContractFile(fileDir, noCityOrAgentContactList.get(0), noCityOrAgentContactList.get(1));
    }

    private static void printNoExistCityContractList(List<FamilyContractDto> allContractList, List<FamilyContractDto> existCityContractList) {

        Map<String, FamilyContractDto> allCityNameContractMap = getCityNameContractMap(allContractList);
        Map<String, FamilyContractDto> existCityNameContractMap = getCityNameContractMap(existCityContractList);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, FamilyContractDto> entry : allCityNameContractMap.entrySet()) {
            if (existCityNameContractMap.get(entry.getKey()) == null) {
                sb.append(entry.getKey()).append("\n");
            }
        }

        System.out.println();
        System.out.println("导入合同中城市总个数：" + allCityNameContractMap.size());
        System.out.println("导入合同中存在系统中的城市总个数：" + existCityNameContractMap.size());
        System.out.println("导入合同中不在系统中的城市名称：\n" + sb.toString());
    }


    /**
     * 输出不在系统中的代理商
     */
    private static List<FamilyContractDto> printNoExistAgent(List<FamilyContractDto> allContractList, List<FamilyContractDto> existAgentList) {

        List<FamilyContractDto> noExistAgentList = new ArrayList<>();
        System.out.println();
        System.out.println("输出不在系统中的代理商==============");
        System.out.println();
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

        System.out.println(sb.toString());

        System.out.println();
        System.out.println("导入代理商总个数=" + allAgentNameMap.size());
        System.out.println("存在代理商个数=" + existAgentNameMap.size());
        System.out.println("不存在代理商个数=" + count);

        return noExistAgentList;
    }

    /**
     * 输出不符合城市和代理商的合同
     */
    private static List<FamilyContractDto> printNoPerfectAgentContract(List<FamilyContractDto> allContractList, List<FamilyContractDto> perfectContractList) {

        List<FamilyContractDto> noPerfectAgentContract = new ArrayList<>();
        System.out.println();
        System.out.println("输出不符合城市和代理商的合同==============");
        System.out.println();
        Map<String, FamilyContractDto> allCityNameContractMap = getCityNameContractMap(allContractList);

        Map<String, FamilyContractDto> perfectCityNameContractMap = getCityNameContractMap(perfectContractList);

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, FamilyContractDto> entry : allCityNameContractMap.entrySet()) {
            if (perfectCityNameContractMap.get(entry.getKey()) == null) {
                count++;
                sb.append("").append(entry.getValue().getAgentCityName()).append("");
                sb.append("  |  ");
                sb.append("").append(entry.getValue().getAgentName()).append("");
                sb.append("\n");

                noPerfectAgentContract.add(entry.getValue());
            } else {
                FamilyContractDto perfectContract = perfectCityNameContractMap.get(entry.getKey());
                FamilyContractDto importContract = entry.getValue();
                if (!(perfectContract.getAgentName().equals(importContract.getAgentName())
                        && perfectContract.getAgentCityName().equals(importContract.getAgentCityName()))) {
                    System.out.println(importContract.getAgentCityName() + " | " + perfectContract.getAgentName() + "|");
                }
            }
        }

        System.out.println(sb.toString());

        System.out.println("导入合同总个数=" + allCityNameContractMap.size());
        System.out.println("符合个数=" + perfectCityNameContractMap.size());
        System.out.println("不符合合同个数=" + count);

        return noPerfectAgentContract;
    }

    /**
     * 输出代理商不在系统中的合同
     */
    private static List<List<FamilyContractDto>> printNoAgentContract(List<FamilyContractDto> noExistAgentList, List<FamilyContractDto> noPerfectAgentContracts) {

        List<FamilyContractDto> noCityContractList = new ArrayList<>();
        List<FamilyContractDto> noAgentContractList = new ArrayList<>();
        System.out.println();
        System.out.println("输出代理商不在系统中的合同==============");
        System.out.println();
        Map<String, FamilyContractDto> noExistAgentListMap = getAgentNameMap(noExistAgentList);

        //Map<String, FamilyContractDto> perfectCityNameContractMap = getCityNameContractMap(noPerfectAgentContracts);

        StringBuilder sb = new StringBuilder();
        StringBuilder sbCity = new StringBuilder();
        int count = 0;
        for (FamilyContractDto noPerfectContract : noPerfectAgentContracts) {
            if (noExistAgentListMap.get(noPerfectContract.getAgentName()) != null) {
                noCityContractList.add(noPerfectContract);

                sb.append("").append(noPerfectContract.getAgentCityName()).append("");
                sb.append("  |  ");
                sb.append("").append(noPerfectContract.getAgentName()).append("");
                sb.append("\n");
            } else {
                noAgentContractList.add(noPerfectContract);
                count++;
                sbCity.append("").append(noPerfectContract.getAgentCityName()).append("");
                sbCity.append("  |  ");
                sbCity.append("").append(noPerfectContract.getAgentName()).append("");
                sbCity.append("\n");
            }
        }

        System.out.println(sb.toString());
        System.out.println();
        System.out.println();
        System.out.println("输出代理商在，但城市不在不在系统中的合同==============");
        System.out.println(sbCity.toString());

        System.out.println();
        System.out.println("导入不符合城市和代理商合同总个数=" + noPerfectAgentContracts.size());
        System.out.println("导入不符合代理商合同总个数=" + count);
        System.out.println("不符合城市的合同个数=" + noCityContractList.size());


        return Arrays.asList(noCityContractList, noAgentContractList);
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

    public static List<FamilyContractDto> handleContractFile(String filePath) {

        XSSFWorkbook workbook;
        List<FamilyContractDto> contractInfoList = new ArrayList<>();
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
                    contractInfoList.add(contractInfo);

                    //替换修改后的代理商
                    if (noExistAgentMap.containsKey(contractInfo.getAgentName())) {
                        agentNameCell.setCellValue(noExistAgentMap.get(contractInfo.getAgentName()));
                        agentNameCell.setCellStyle(agentCellStyle);
                        replaceAgentSet.add(contractInfo.getAgentName());
                    }
                }
            }
            //获取代理商名称
            System.out.println("读取合同个数 = " + contractInfoList.size());
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

            OutputStream out = new FileOutputStream(new File(outModifyAgentFilePath));
            workbook.write(out);
            out.close();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束处理合同文件。。。。");
        return contractInfoList;
    }

    private static String getCellValue(XSSFCell cell) {
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


    /**
     * 获得满足条件的合同
     *
     * @param filePath
     * @return
     */
    public static List<FamilyContractDto> handlePerfectContractFile(String filePath) {

        HSSFWorkbook workbook;
        List<FamilyContractDto> contractInfoList = new ArrayList<>();
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
                    contractInfo.setAgentCityName(getCellStringValue(xssfRow.getCell(1)).trim());

                    contractInfo.setAgentId(Long.parseLong(getCellStringValue(xssfRow.getCell(2))));
                    contractInfo.setAgentName(getCellStringValue(xssfRow.getCell(3)).trim());


                    if (allExistCityAgentMap.get(contractInfo.getAgentCityName()) != null) {
                        repeatCityList.add(contractInfo.getAgentCityName() + " | " + contractInfo.getAgentName());
                    }
                    allExistCityAgentMap.put(contractInfo.getAgentCityName(), contractInfo.getAgentName());

                    agentCityMap.put(contractInfo.getAgentName(), contractInfo.getAgentCityName());
                    contractInfoList.add(contractInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("一共处理条件代理商和城市关系的个数：" + contractInfoList.size());
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

        return contractInfoList;
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
        List<FamilyContractDto> contractInfoList = new ArrayList<>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            HSSFSheet xssfSheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    contractInfo.setAgentId(Long.parseLong(getCellStringValue(xssfRow.getCell(0))));
                    contractInfo.setAgentName(getCellStringValue(xssfRow.getCell(1)).trim());
                    contractInfo.setId(Long.parseLong(getCellStringValue(xssfRow.getCell(2))));

                    allExistAgentMap.put(contractInfo.getAgentName(), contractInfo.getAgentId());
                    contractInfoList.add(contractInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("一共处理已经存在的代理商个数：" + contractInfoList.size());
        System.out.println("结束处理已经存在的代理商。。。");
        return contractInfoList;
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
            Map<String, FamilyContractDto> noCityContractMap = getCityNameContractMap(noCityContactList);
            Map<String, FamilyContractDto> noAgentContractMap = getCityNameContractMap(noAgentContactList);

            System.out.println();
            System.out.println("开始生成处理结果合同文件。。。。");
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    FamilyContractDto contractInfo = new FamilyContractDto();
                    XSSFCell cityName = xssfRow.getCell(3);
                    XSSFCell agentName = xssfRow.getCell(4);

                    contractInfo.setAgentCityName(cityName.toString().trim().replaceAll("\u200B", "").replaceAll(" ", ""));
                    contractInfo.setAgentName(agentName.toString().trim().replaceAll("\u200B", "").replaceAll(" ", ""));

                    //
                    FamilyContractDto noCityContract = noCityContractMap.get(contractInfo.getAgentCityName());
                    FamilyContractDto noAgentContract = noAgentContractMap.get(contractInfo.getAgentCityName());

                    if (noCityContract != null && noCityContract.getAgentName().equals(contractInfo.getAgentName())) {
                        noCityCount++;
                        cityName.setCellValue(contractInfo.getAgentCityName());
                        cityName.setCellStyle(cityCellStyle);
                    }
                    if (noAgentContract != null && noAgentContract.getAgentName().equals(contractInfo.getAgentName())) {
                        noAgentCount++;
                        agentName.setCellValue(contractInfo.getAgentName());
                        agentName.setCellStyle(agentCellStyle);
                        cityName.setCellStyle(agentCellStyle);
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
