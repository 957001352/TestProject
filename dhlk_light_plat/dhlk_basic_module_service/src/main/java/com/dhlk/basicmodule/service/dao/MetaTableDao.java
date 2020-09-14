package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.DataBroker;
import com.dhlk.entity.hive.MetaTable;
import com.dhlk.entity.hive.MetaTableColumn;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 接口管理
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface MetaTableDao {

    Integer insert(MetaTable metaTable);

    Integer update(MetaTable metaTable);

    Integer delete(List<String> ids);

    List<MetaTable> findList();


    List<MetaTable> findAllList();

    Integer insertColumn(MetaTableColumn metaTableColumn);

    Integer updateColumn(MetaTableColumn metaTableColumn);

    Integer deleteColumn(List<String> ids);

    List<MetaTableColumn> findColumnList(Integer tableId);


    Integer executeSql(String sql);

    List<String> findTableList();

    List<String> findTableColumnList(String tableName);
}