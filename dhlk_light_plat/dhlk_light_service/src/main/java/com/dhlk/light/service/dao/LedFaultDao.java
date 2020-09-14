package com.dhlk.light.service.dao;

import com.dhlk.entity.light.FaultCode;
import com.dhlk.entity.light.LedFault;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
* @Description:    故障信息上报Dao
* @Author:         gchen
* @CreateDate:     2020/6/10 10:46
*/
@Repository
public interface LedFaultDao {

   /**
   * 保存故障信息
   * @author      gchen
   * @param ledFault
   * @date        2020/6/10 10:51
   */
    Integer insert(LedFault ledFault);

    /**
     * 查询故障信息
     * @author      gchen
     * @param ledSn
     * @date        2020/6/10 10:51
     */
    List<LedFault> findList(@Param("ledSn") String ledSn);


    /**
     * 导出列表查询
     */
    List<LinkedHashMap<String, Object>> findExportList(String ledSn);
}
