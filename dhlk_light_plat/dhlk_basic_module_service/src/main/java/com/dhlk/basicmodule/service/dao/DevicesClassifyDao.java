package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.DevicesClassify;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description:    设备分类管理
 * @Author:         gchen
 * @CreateDate:     2020/3/30 14:02
 * @UpdateUser:     gchen
 * @UpdateDate:     2020/3/30 14:02
 * @Version:        1.0
 */
@Repository
public interface DevicesClassifyDao {

    Integer insert(DevicesClassify devicesClassify);

    Integer update(DevicesClassify devicesClassify);

    Integer delete(String id);

    List<DevicesClassify> findList(@Param("classifyName") String classifyName, @Param("tenantId") Integer tenantId, @Param("attachPath") String attachmentPath);


    List<LinkedHashMap<String,String>> findAttrSetByClassifyById(Integer devicesClassifyId);

    List<LinkedHashMap<String,String>> findAttrByClassifyById(String devicesClassifyId);

    Integer isRepeatName(DevicesClassify devicesClassify);


    DevicesClassify findDevicesClassifyById(String id);

}
