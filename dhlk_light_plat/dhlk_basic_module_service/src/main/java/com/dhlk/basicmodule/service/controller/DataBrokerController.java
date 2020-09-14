package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.DataBrokerService;
import com.dhlk.entity.basicmodule.DataBroker;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 消息订阅mosquito管理
*/
@RestController
@RequestMapping(value = "/dataBroker")
public class DataBrokerController {
    @Autowired
    private DataBrokerService dataBrokerService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("dataBroker:save")
    public Result save(@RequestBody DataBroker dataBroker) {
        return dataBrokerService.save(dataBroker);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("dataBroker:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return dataBrokerService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    @RequiresPermissions("dhlk:view")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  dataBrokerService.findPageList(pageNum, pageSize);
    }

}
