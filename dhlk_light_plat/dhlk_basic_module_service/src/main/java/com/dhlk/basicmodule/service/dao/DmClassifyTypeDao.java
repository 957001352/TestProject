package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.dm.DmClassifyType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 接口管理
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface DmClassifyTypeDao {

    Integer insert(DmClassifyType dmClassifyType);

    Integer update(DmClassifyType dmClassifyType);

    Integer delete(List<String> ids);

    List<DmClassifyType> findList(String name);
    /*
     * 判断名称是否重复
     * @return
     */
    Integer isRepeatName(DmClassifyType dmClassifyType);
}