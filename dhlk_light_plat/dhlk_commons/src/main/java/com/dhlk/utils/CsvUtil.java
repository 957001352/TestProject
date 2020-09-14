package com.dhlk.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Description csv文件操作
 * @Author lpsong
 * @Date 2020/3/30
 */
public class CsvUtil {
    private static final String FORMAT_VALUE_HM = "yyyyMMddHHmm";
    /**
     * 读取Csv文件 csv
     * @param clas   目标对象
     * @param path   文件路径
     * @param startTag  表头行标记
     * @return
     */
    public static List<Object> importCsv(Class clas, String path, String startTag){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(path));
            CSVReader reader = new CSVReader(new InputStreamReader(in,"gbk"));
            String [] nextLine;
            boolean start = false;
            List<String> headList = new ArrayList<>();
            List<Object> dataList = new ArrayList<>();

            String electricity = "",voltageF = "",voltageR = "";
            while((nextLine = reader.readNext()) != null) {
                if(start){
                    Object obj = clas.newInstance();
                    Field[] userFields = clas.getDeclaredFields();
                    for (int i = 0; i <userFields.length ; i++) {
                        // AccessibleTest类中的成员变量为private,故必须进行此操作
                        // 取消属性的访问权限控制，即使private属性也可以进行访问。
                        userFields[i].setAccessible(true);
                        // 将指定对象变量上此Field对象表示的字段设置为指定的新值。
                        String type = userFields[i].getGenericType().toString();
                        if("electricity".equals(userFields[i].getName().toLowerCase())){
                            userFields[i].set(obj, electricity.replace("@",""));
                        }else if("voltagef".equals(userFields[i].getName().toLowerCase())){
                            userFields[i].set(obj, voltageF);
                        }else if("voltager".equals(userFields[i].getName().toLowerCase())){
                            userFields[i].set(obj, voltageR);
                        }
                        Integer index = headList.indexOf(userFields[i].getName().toLowerCase());
                        // 属性不存在跳过
                        if(-1 == index) continue;
                        if(index >= nextLine.length) break;
                        String value = nextLine[index];
                        // 判断属性类型
                        // 判断属性类型
                        if ("class java.lang.String".equals(type)) {
                            // 如果type是类类型，则前面包含"class "，后面跟类名
                            userFields[i].set(obj,value);
                        }else if ("class java.lang.Integer".equals(type)) {
                            userFields[i].set(obj,Integer.valueOf(!CheckUtils.isNumeric(value) ? "0" : value));
                        }else if ("class java.lang.Double".equals(type)) {
                            userFields[i].set(obj,!CheckUtils.isNumeric(value) ? null : Double.valueOf(value));
                        }
                    }
                    dataList.add(obj);
                }else{
                    // 测试电流解析
                    if(nextLine[0].equals("At")){
                        electricity = nextLine[2].split(",")[4];
                    }else if(nextLine[0].equals("CustomerNote1")){
                        //ESD测试电压
                        voltageF = nextLine[2].split("_")[1].split("=")[1];
                        voltageR = nextLine[2].split("_")[6].split("=")[1];
                    }
                }
                // 数据头部开始标记
                if(nextLine[0].equals(startTag)) {
                    start = true;
                    Arrays.asList(nextLine).stream().forEach(column -> headList.add(column.toLowerCase()));
                }
            }
            return dataList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Csv文件
     * @param path   文件路径
     * @return
     */
    public static CSVReader read(String path){
        try {
             return new CSVReader(new FileReader(path));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private  static String fileNameSuffix(String fileName){
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_VALUE_HM);
        String suffix= sf.format(new Date()) + (int) (Math.random() * 900 + 100)+".csv";
        if(!CheckUtils.isNull(fileName)){
          return    fileName=fileName+suffix;
        }
        return suffix;
    }
    public static  void exportCsv(String fileName, Object data, String[] header, HttpServletResponse response) {
        // 设置默认文件名为当前时间：年月日时分秒
        fileName=fileNameSuffix(fileName);
        try {
            // 设置response头信息
            response.reset(); // 清空输出流
            response.setContentType("APPLICATION/OCTET-STREAM"); // 定义输出类型
            fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;  filename="+fileName);
            response.setCharacterEncoding("gbk");

            //向csv写数据
            CSVWriter writer =  new CSVWriter(response.getWriter());
            writer.writeNext(header);
            // 组装数据并输出
            writer.writeAll(fillCsv((List<LinkedHashMap<String, Object>>) data,header.length));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 向csv中填充数据
     */
    private static  List<String[]> fillCsv(List<LinkedHashMap<String,Object>> exportResults,int size) throws Exception {
        List<String[]> datas=new ArrayList<String[]>();
        String [] data=null;
        for(LinkedHashMap<String,Object> map:exportResults){
            int i=0;
            data=new String[size];
            for(String key : map.keySet()){
                Object value = map.get(key);
                if(value!=null){
                    data[i]= value+"";
                }
                i++;
            }
            datas.add(data);
        }
        return datas;
    }
}
