package com.dhlk.light.factory.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface MqttSyncDao {
    List<LinkedHashMap<String,String>> findFields(@RequestParam("tableName") String tableName);

    Integer executeSql(@Param("sql") String sql);
}
