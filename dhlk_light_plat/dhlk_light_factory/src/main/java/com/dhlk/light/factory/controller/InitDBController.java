package com.dhlk.light.factory.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.factory.service.InitDBService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Des 初始化数据库
 * @Author xkliu
 * @Date 2020/9/29
 **/
@RestController
@RequestMapping("/initDB")
public class InitDBController {

    @Autowired
    private InitDBService initDBService;

    /**
     * 执行本地初始化sql脚本
     *
     * @return
     */
    @ApiOperation("执行本地初始化sql脚本")
    @GetMapping("/execSQL")
    @RequiresAuthentication
    public Result execSQL() {
        return initDBService.execSQL();
    }


    /**
     * 删除本地redis中所有的key
     *
     * @return
     */
    @ApiOperation("删除本地redis中所有的key")
    @GetMapping("/delRedisKeys")
    @RequiresAuthentication
    public Result delRedisKeys() {
        return initDBService.delRedisKeys();
    }


    @ApiOperation("删除数据库和Reids")
    @GetMapping("/cleanData")
    @RequiresAuthentication
    public Result cleanData() {
        return initDBService.cleanData();
    }
}
