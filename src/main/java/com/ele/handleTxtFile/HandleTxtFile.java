package com.ele.handleTxtFile;

import org.codehaus.jackson.util.BufferRecycler;

import java.io.*;

/**
 * 将txt文件存入数据库
 *
 * @author oukailiang
 * @create 2016-10-11 下午5:19
 */

public class HandleTxtFile {
    private static final String filePath = "/Users/oukailiang/Downloads/hackathon/E_Data_handled/";
    //
    private static final String fileName = "his_eco_env";
    private static final String fileNameDir = "his_eco_env/";

    public static void txtToTable(File file, File destfile) {
        BufferedReader br = null;
        FileReader fr = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String line;
        int count = 0;
        try {
            //读
            fr = new FileReader(file);
            br = new BufferedReader(fr);//构造一个BufferedReader类来读取文件
            //写
            fw = new FileWriter(destfile, true);
            bw = new BufferedWriter(fw);
            //表头
            String header = br.readLine();
            //+ getTableColAndValues(header, true)
            String sql = "insert into " + getTableName(file.getName()) + " values";
            int num = 1;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                count++;
                if (num == 1) {
                    sb.append(sql);
                }
                //
                if (!(num == 50)) {
                    sb.append(getTableColAndValues(line, false));
                    sb.append(",");
                    num++;
                    continue;
                }
                sb.append(getTableColAndValues(line, false));
                System.out.println("num=" + num);
                System.out.println("count=" + count);
                num = 1;
                sb.append(";\n");
                System.out.println(sb.toString());
                //每10万条数据写入一个文件
                if ((count % 30000) == 0) {
                    //bw.write(line);
                    bw.write(sb.toString());
                    bw.flush();

                    int index = count / 30000;
                    destfile = new File(filePath + fileNameDir + fileName + index + ".sql");
                    destfile.createNewFile();
                    fw = new FileWriter(destfile, true);
                    bw = new BufferedWriter(fw);
                    sb = new StringBuffer();
                } else {
                    //bw.write(line+"\n");
                    bw.write(sb.toString());
                    sb = new StringBuffer();
                    break;
                }
            }
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();

                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过文件名获得表的名称
     *
     * @param fileName
     * @return
     */
    private static String getTableName(String fileName) {
        int pointIndex = fileName.lastIndexOf(".");
        System.out.println(pointIndex);
        return fileName.substring(0, pointIndex);
    }

    /**
     * 拼接表列属性和values值
     *
     * @param column
     * @return
     */
    private static String getTableColAndValues(String column, boolean flag) {
        StringBuffer sb = new StringBuffer();
        String[] col = column.split("\t");
        int len = col.length;
        sb.append("(");
        for (int i = 0; i < len; i++) {
            //true 需要去除两边的冒号,用于拼接列属性时
            if (flag) {
                sb.append(removeColon(col[i]));
            } else {
                sb.append(col[i]);
            }
            if (i != len - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 去除列值两边的冒号
     *
     * @param colValue
     * @return
     */
    private static String removeColon(String colValue) {
        int len = colValue.length();
        return colValue.substring(1, len - 1);
    }

    public static void readFile(String filePath) {
        try {
            //读
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);//构造一个BufferedReader类来读取文件
            String[] oneRow = br.readLine().split("\t");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < oneRow.length; i++) {
                sb.append(oneRow[i] + ",");
            }
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    public static void main(String[] args) throws IOException {
        File file = new File(filePath + fileName + ".txt");
        File destfile = new File(filePath + fileNameDir + fileName + "0.sql");
        if (!destfile.exists()) {
            // destfile.createNewFile();
        }
        // txtToTable(file, destfile);
        readFile(filePath + "his_order_info.txt");

    }
}
