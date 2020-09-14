package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.api.ApiClassify;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 接口分类
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface ApiClassifyDao {

    Integer insert(ApiClassify apiClassify);

    Integer update(ApiClassify apiClassify);

    Integer delete(List<String> ids);

    List<ApiClassify> findList(Integer parentId);
    /*
     * 判断分类名称是否重复
     * @return
     */
    Integer isRepeatName(ApiClassify apiClassify);

}