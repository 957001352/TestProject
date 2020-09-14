package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.web.light.service.fbk.ConstructionServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/3
 * <p>
 * 施工信息 service
 */
@FeignClient(value = "light-service/construction", fallback = ConstructionServiceFbk.class)
public interface ConstructionService {

    /**
     * 保存施工信息
     *
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Construction construction);

    /**
     * 列表查询
     *
     * @param implPeople
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "implPeople", required = false) String implPeople,
                    @RequestParam(value = "startDate", required = false) String startDate,
                    @RequestParam(value = "endDate", required = false) String endDate,
                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);
}
