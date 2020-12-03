package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.web.light.service.fbk.AreaServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域service
 */
@FeignClient(value = "light-service/area", fallback = AreaServiceFbk.class)
public interface AreaService {

    /**
     * 保存施工区域
     *
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Area area);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 获取当前租户施工区域列表
     *
     * @return
     */
    @GetMapping("/findList")
    Result findList();

    /**
     * 根据租户Id获取施工区域
     * @param tenantId
     * @return
     */
    @GetMapping("/findListByTenantId")
    Result findListByTenantId(@RequestParam(value = "tenantId", required = false) Integer tenantId);

    /**
     * 验证区域名称重复
     * @param name
     * @return
     */
    @GetMapping("/findAreaRepeat")
    Result findAreaRepeat(@RequestParam(value = "name") String name);

    /**
     * 修改图纸
     * @param area
     * @return
     */
    @PostMapping("/update")
    Result update(@RequestBody Area area);
}
