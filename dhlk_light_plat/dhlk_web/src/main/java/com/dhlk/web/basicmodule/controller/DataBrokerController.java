package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.DataBroker;
import com.dhlk.web.basicmodule.service.DataBrokerService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 消息订阅mosquito管理
*/
@RestController
@RequestMapping(value = "/dataBroker")
@Api(description = "消息订阅mosquito管理")
public class DataBrokerController {
    @Autowired
    private DataBrokerService dataBrokerService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody DataBroker dataBroker) {
        return dataBrokerService.save(dataBroker);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return dataBrokerService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  dataBrokerService.findPageList(pageNum, pageSize);
    }

}
