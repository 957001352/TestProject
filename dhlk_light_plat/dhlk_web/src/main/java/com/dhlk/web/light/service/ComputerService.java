package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.dhlk.entity.light.Computer;
import com.dhlk.web.light.service.fbk.ComputerServiceFbk;
import com.dhlk.web.light.service.fbk.SwitchServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "light-service/computer", fallback = ComputerServiceFbk.class)
public interface ComputerService {
    /**
     * 保存/修改
     * @author      gchen
     * @param computer
     * @return
     * @date        2020/6/4 15:50
     */
    @PostMapping("/save")
    Result save(@RequestBody Computer computer);

    /**
     * 批量删除
     * @author gchen
     * @param ids
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/delete")
    Result delete(@RequestParam("ids") String ids);

    /**
     * 列表查询
     * @author      gchen
     * @param name pageNum pageSize
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name",required = false) String name,
                    @RequestParam(value = "pageNum",required = false) Integer pageNum,
                    @RequestParam(value = "pageSize",required = false) Integer pageSize);

    /**
     * 添加代理
     * @author      gchen
     */
    @GetMapping("/addReseller")
    Result addReseller(@RequestParam("biProxyServerInfo") String biProxyServerInfo,
                       @RequestParam("mac") String mac);
}
