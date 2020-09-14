package com.dhlk.light.service.dao;

import com.dhlk.entity.light.FaultCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/4
 * @描述  故障代码Dao
 */
@Repository
public interface FaultCodeDao {


    Integer insert(FaultCode faultCode);

    Integer update(FaultCode faultCode);

    Integer delete(List<String> asList);

    List<FaultCode> findList(@Param("name") String name, @Param("code") String code,@Param("id") Integer id,@Param("flag") int flag);

    FaultCode findByCode(@Param("code")String code);
}
