package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.DevicesAttrSet;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description:    设备属性集合管理
 * @Author:         gchen
 * @CreateDate:     2020/3/30 14:02
 * @UpdateUser:     gchen
 * @UpdateDate:     2020/3/30 14:02
 * @Version:        1.0
 */
@Repository
public interface DevicesAttrSetDao {

    Integer insert(DevicesAttrSet devicesAttrSet);

    Integer update(DevicesAttrSet devicesAttrSet);

    Integer delete(Integer id);

    List<DevicesAttrSet> findList(String name,Integer tenantId);

    //判断属性集合名称是否重复 不重复返回0，返回其他重复
    Integer isRepeatAttr(DevicesAttrSet devicesAttrSet);

    DevicesAttrSet findById(Integer id);
}
