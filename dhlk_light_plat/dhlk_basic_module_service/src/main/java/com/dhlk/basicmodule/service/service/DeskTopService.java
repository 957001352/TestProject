package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DeskTop;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */
public interface DeskTopService {

    Result save(@RequestBody List<DeskTop> deskTopList);
    Result delete(String ids);
    Result findList(Integer userId ,Integer pageNum, Integer pageSize);
}
