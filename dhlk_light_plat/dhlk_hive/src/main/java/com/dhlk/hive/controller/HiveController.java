package com.dhlk.hive.controller;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.entity.hive.MetaTableColumn;
import com.dhlk.hive.util.HDFSUitls;
import com.dhlk.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/18
 */
@RestController
@RequestMapping(value = "/hive")
public class HiveController {

    @Autowired
    @Qualifier("hiveDruidDataSource")
    DataSource druidDataSource;

    @Value("${hive.dataType}")
    private String dataType;

    @Value("${hive.hiveDatabase}")
    private String hiveDatabase;

    @GetMapping("/findTableList")
    public Result findTableList() throws SQLException {
        List<String> list = new ArrayList<String>();
        Statement statement = druidDataSource.getConnection().createStatement();
        String sql = "show tables";
        ResultSet res = statement.executeQuery(sql);
        while (res.next()) {
            list.add(res.getString(1));
        }
        return ResultUtils.success(list);
    }



    /**
     * createTable
     * @return result
     */
    @PostMapping(value = "/createTable")
    public void createTable(@RequestBody List<MetaTable> tableList) throws Exception {
        Statement stmt = druidDataSource.getConnection().createStatement();
        stmt.execute("create database if not exists " + hiveDatabase);

        for(MetaTable table:tableList){

            StringBuffer dropsql = new StringBuffer();
            dropsql.append("DROP TABLE IF EXISTS ");
            dropsql.append(" hive_meta_" + table.getTableEnname());
            stmt.execute(dropsql.toString());



            StringBuffer createsql = new StringBuffer();
            createsql.append("create external table ");
            createsql.append("hive_meta_" + table.getTableEnname());
            createsql.append("(");

            List<MetaTableColumn> columnList =table.getColumnList();
            columnList.stream().forEach(column ->{
                createsql.append(column.getColumnEnname() + " ");
                String type = converType(column.getDataType());
                if("varchar".equals(type)){
                    createsql.append(type + "(" + column.getLength() + "),");
                }else{
                    createsql.append(type + ",");
                }
            });
            createsql.deleteCharAt(createsql.length()-1);
            createsql.append(") row format delimited fields terminated by '\\t' location ");
            createsql.append("'/dhlk/hive_meta/");
            createsql.append("hive_meta_"+table.getTableEnname() + "'");
            stmt.execute(createsql.toString());
            System.err.println("HIVE-SQL：" + createsql.toString());
        }
        stmt.close();
    }

    /**
     * dropTable
     *
     * @return result
     */
    @PostMapping(value = "/dropTable")
    public void dropTable(@RequestBody List<MetaTable> tableList) throws Exception {
        Statement stmt = druidDataSource.getConnection().createStatement();
        for(MetaTable table:tableList){
            StringBuffer stb = new StringBuffer();
            stb.append("DROP TABLE IF EXISTS ");
            stb.append("hive_meta_" + table.getTableEnname());
            stmt.execute(stb.toString());
        }
        stmt.close();
    }




    // 创建HDFS目录
    private void mkdirHDFS(List<MetaTable> tableList) throws Exception {
        String hdfsUrl = "hdfs://cluster1";
        for(MetaTable table:tableList){
            String hdfsPath = "/yk/meta/" + table.getTableEnname() + "/";
            HDFSUitls.mkdir(hdfsPath, hdfsUrl);
        }
    }

    // 删除HDFS目录
    private void rmoveHDFS(List<MetaTable> tableList) throws Exception {
        String hdfsUrl = "hdfs://cluster1";
        for(MetaTable table:tableList){
            String hdfsPath = "/yk/meta/" + table.getTableEnname();
            HDFSUitls.removeFile(hdfsPath, hdfsUrl);
        }
    }

    // Hive不支持数据类型转换
    private String converType(String type){
        if(dataType.contains(type)){
            String[] strs = dataType.split(",");
            for (int i=0; i<strs.length; i++) {
                if(strs[i].contains(type.toLowerCase())){
                    return strs[i].split("-")[1];
                }
            }
        }
        return type;
    }
}