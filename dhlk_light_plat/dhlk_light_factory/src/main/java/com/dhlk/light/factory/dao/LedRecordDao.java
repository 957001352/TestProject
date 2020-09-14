package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.LedRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 灯
 * @Author lpsong
 * @Date 2020/6/4
 */
@Repository
public interface LedRecordDao {

    Integer insert(LedRecord ledRecord);


    Integer updateBackResultByTs(@Param("list") List<LedRecord> list,@Param("ts") String ts);

    Integer updateBackResultByCmdType(@Param("list") List<LedRecord> list,@Param("commond") String commond);



    List<LedRecord> findList(@Param("sn") String sn,
                             @Param("commond") String commond,
                             @Param("sendResult") String sendResult,
                             @Param("backResult") String backResult,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime);
   Integer delete(String date);

}