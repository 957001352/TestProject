package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DeskTop;
import com.dhlk.web.light.service.fbk.LightDeskTopServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @*/
@FeignClient(value = "light-service/deskTop" , fallback = LightDeskTopServiceFbk.class)
public interface LightDeskTopService {

    /**
     * 新增
     * @param deskTopList
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody List<DeskTop> deskTopList);

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids);


    /**
     * 查询
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/findList")
    Result findList(@RequestParam(value = "userId", required = false) Integer userId,
                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);
}
