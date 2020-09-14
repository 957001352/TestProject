package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.web.light.service.fbk.GroupServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "light-service/group", fallback = GroupServiceFbk.class)
public interface GroupService {

    /**
     * 保存/修改
     * @author      gchen
     * @param swich
     * @return
     * @date        2020/6/4 15:50
     */
    @PostMapping("/save")
    Result save(@RequestBody Switch swich);

    /**
     * 删除
     * @author gchen
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/delete")
    Result delete(@RequestParam("id") String id);

    /**
     * 查询
     * @author      gchen
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/findList")
    Result findList(@RequestParam("areaId") String areaId);
}
