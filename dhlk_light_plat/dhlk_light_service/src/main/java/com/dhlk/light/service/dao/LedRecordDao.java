package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.LedOnline;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LedRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 灯
 * @Author lpsong
 * @Date 2020/6/4
 */
@Repository
public interface LedRecordDao {
    Integer insert(LedRecord ledRecord);


    Integer updateBackResultByTs(@Param("sn") String sn,@Param("ts") String ts,@Param("backResult") String backResult,@Param("backTime") String backTime);

    Integer updateBackResultByCmdType(@Param("sn") String sn,@Param("commond") String commond,@Param("backResult") String backResult,@Param("backTime") String backTime);

    List<LedRecord> findList(@Param("tenantId") Integer  tenantId,
                             @Param("sn") String sn,
                             @Param("commond") String commond,
                             @Param("sendResult") String sendResult,
                             @Param("backResult") String backResult,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime);
    Integer delete(String date);


}