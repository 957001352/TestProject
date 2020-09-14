package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.DevicesAttrDetail;
import com.dhlk.entity.basicmodule.DevicesClassifyDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:    设备类型属性明细管理
 * @Author:         gchen
 * @CreateDate:     2020/3/30 14:02
 * @UpdateUser:     gchen
 * @UpdateDate:     2020/3/30 14:02
 * @Version:        1.0
 */
@Repository
public interface DevicesClassifyDetailDao {

    Integer insert(DevicesClassifyDetail devicesClassifyDetail);

    Integer update(DevicesClassifyDetail devicesClassifyDetail);

    Integer delete(Integer id);

    List<DevicesClassifyDetail> findList(Integer id);

    //根据设备分类查询设备类型属性明细
    List<DevicesClassifyDetail> findDevicesClassifyDetailByDevicesClassifyId(Integer devicesClassifyId);

    //判断设备属性集合是否与设备分类关联 返回0代表无关联，其他就关联
    Integer findDevicesClassifyDetailByAttrSetId(Integer attrSetId);

    //插入类型属性明细集合
    Integer insertDevicesClassifyDetails(@Param("classifyDetails") List<DevicesClassifyDetail> classifyDetails);

    //删除设备分类下的所有设备属性明细
    Integer deleteByDevicesClassifyId(String devicesClassifyId);


    Integer findDevicesClassifyDetailByDetail(@Param("devicesAttrDetailIds") List<Integer> devicesAttrDetailIds);
}
