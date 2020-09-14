package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.DevicesAttrDetail;
import com.dhlk.entity.basicmodule.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description:    设备属性明细管理
 * @Author:         gchen
 * @CreateDate:     2020/3/30 14:02
 * @UpdateUser:     gchen
 * @UpdateDate:     2020/3/30 14:02
 * @Version:        1.0
 */
@Repository
public interface DevicesAttrDetailDao {

    Integer insert(DevicesAttrDetail devicesAttrDetail);

    Integer update(DevicesAttrDetail devicesAttrDetail);

    Integer delete(List<Integer> devicesAttrDetailIds);

    List<DevicesAttrDetail> findList(Integer id);

    //查询属性集合下的属性明细
    List<DevicesAttrDetail> findAttrDetailsByAttrSetId(Integer attrSetId);

    //插入属性明细集合
    Integer insertDevicesAttrDetails(@Param("devicesAttrDetails")List<DevicesAttrDetail> devicesAttrDetails,@Param("id")Integer id);

    Integer deleteByAttrSetId(Integer attrSetId);

}
