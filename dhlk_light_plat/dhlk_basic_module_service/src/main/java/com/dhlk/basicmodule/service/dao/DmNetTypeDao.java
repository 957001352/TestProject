package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.dm.DmNetType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 设备类型
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface DmNetTypeDao {

    Integer insert(DmNetType dmNetType);

    Integer update(DmNetType dmNetType);

    Integer delete(List<String> ids);

    List<DmNetType> findList(String name);
    /*
     * 判断名称是否重复
     * @return
     */
    Integer isRepeatName(DmNetType dmNetType);
}