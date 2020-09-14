package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.MetaTableDao;
import com.dhlk.basicmodule.service.service.HiveTableManagerService;
import com.dhlk.entity.hive.MetaTable;
import com.dhlk.entity.hive.MetaTableColumn;
import com.dhlk.domain.Result;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.List;

/**
 * @Description hive数据表管理
 * @Author lpsong
 * @Date 2020/3/16
 */
@Service
public class HiveTableManagerServiceImpl implements HiveTableManagerService {
    @Autowired
    private MetaTableDao metaTableDao;


    @Override
    public Result createTable(Boolean conver) throws MyException {
        //查询hive元数据数据表
        List<MetaTable> tableList= metaTableDao.findAllList();
        if(tableList!=null){
            //如果初始化，则先删除所有hive相关表
            if(conver){
                for(MetaTable table:tableList){
                    String sql= this.dropTableSql(table.getTableEnname());
                    metaTableDao.executeSql(sql);
                }
            }
            //查询mysql数据库中已经创建的hive数据表
            List<String> mysqlTableList= metaTableDao.findTableList();
            for(MetaTable table:tableList){
                if(table!=null&&!CheckUtils.isNull(table.getTableEnname())){
                    //如果数据库存在表则修改，不过不存在则新增
                    if(mysqlTableList!=null&&mysqlTableList.contains("hive_meta_"+table.getTableEnname())){
                        this.alterTableSql(table);
                    }else{
                        String sql= this.createTableSql(table);
                        metaTableDao.executeSql(sql);
                    }
                }
            }
        }
        return ResultUtils.success(tableList);
    }
    /**
    * 生产删除表语句
     * @param tableName
    * @return
    */
    private  String dropTableSql(String tableName){
        return  "drop table if exists hive_meta_"+tableName;
    }
    /**
    * 生产创建表sql
     * @param table
    * @return
    */
    private  String createTableSql(MetaTable table){
        StringBuffer sql=new StringBuffer();
        sql.append("create table  hive_meta_"+table.getTableEnname()+" (");
        if(table.getColumnList()!=null){
            List<MetaTableColumn> colList=table.getColumnList();
            sql.append("id integer  NOT NULL AUTO_INCREMENT COMMENT 'id',");
            for(MetaTableColumn col:colList){
                if(col!=null){
                    if(CheckUtils.isNull(col.getLength())){
                        sql.append(col.getColumnEnname()+" "+col.getDataType()+"  COMMENT '" +col.getColumnCnname()+"', ");
                    }else{
                        sql.append(col.getColumnEnname()+" "+col.getDataType()+"("+col.getLength()+")  COMMENT  '" +col.getColumnCnname()+"', ");
                    }
                }
            }
            sql.append("primary key  (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='"+table.getTableCnname()+"';");
        }
        return sql.toString();
    }
    /**
    * 生产修改表sql  表存在，则修改列
     * @param table
    * @return
    */
    private  void alterTableSql(MetaTable table){
        if(table.getColumnList()!=null){
            List<MetaTableColumn> colList=table.getColumnList();
            List<String> mysqlColList= metaTableDao.findTableColumnList(table.getTableEnname());
            for(MetaTableColumn col:colList){
                if(col!=null){
                    String sql=null;
                    //如果数据库存在列 则修改，不存在则进行
                    if(mysqlColList!=null&&mysqlColList.contains(col.getColumnEnname())){
                        sql= this.modifyColumnSql(table.getTableEnname(),col);
                    }else{
                        sql= this.addColumnSql(table.getTableEnname(),col);
                    }
                    if(!CheckUtils.isNull(sql)){
                        metaTableDao.executeSql(sql.toString());
                    }
                }
            }

        }
    }
    /**
    * 生产新增列语句
     * @param tableName
     * @param col
    * @return
    */
    private String addColumnSql(String tableName,MetaTableColumn col){
        StringBuffer sql=new StringBuffer();
        if(CheckUtils.isNull(col.getLength())){
            sql.append("alter table hive_meta_"+tableName +" add "+col.getColumnEnname()+" "+col.getDataType()+"  COMMENT '" +col.getColumnCnname()+"';");
        }else{
            sql.append("alter table hive_meta_"+tableName +" add "+col.getColumnEnname()+" "+col.getDataType()+"("+col.getLength()+")  COMMENT  '" +col.getColumnCnname()+"';");
        }
        return sql.toString();
    }
    /**
    * 生成修改列语句
     * @param tableName
     * @param col
    * @return
    */
    private String modifyColumnSql(String tableName,MetaTableColumn col){
        StringBuffer sql=new StringBuffer();
        if(CheckUtils.isNull(col.getLength())){
            sql.append("alter table hive_meta_"+tableName +" modify "+col.getColumnEnname()+" "+col.getDataType()+"  COMMENT '" +col.getColumnCnname()+"';");
        }else{
            sql.append("alter table hive_meta_"+tableName +" modify "+col.getColumnEnname()+" "+col.getDataType()+"("+col.getLength()+")  COMMENT  '" +col.getColumnCnname()+"';");
        }
        return sql.toString();
    }
    @Override
    public Result dropTable() throws MyException {
        List<MetaTable> tableList= metaTableDao.findAllList();
        for(MetaTable table:tableList){
            if(table!=null&&!CheckUtils.isNull(table.getTableEnname())){
                metaTableDao.executeSql(this.dropTableSql(table.getTableEnname()));
            }
        }
        return  ResultUtils.success(tableList);
    }
    @Override
    public Result findTableList() throws MyException {
        return ResultUtils.success(metaTableDao.findAllList());
    }
}