package com.dhlk.basicmodule.service.dao;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description 接口管理
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface HiveMetaDao {

    List<LinkedHashMap<String,Object>> findList(String table);


    List<LinkedHashMap<String,Object>> findColumnList(String table);

}