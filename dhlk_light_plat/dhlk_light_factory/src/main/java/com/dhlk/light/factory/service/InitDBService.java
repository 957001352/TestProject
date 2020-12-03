package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;

/**
 * @Des InitDBService
 * @Author xkliu
 * @Date 2020/9/29
 **/
public interface InitDBService {

    /**
     * 执行sq执行本地初始化sql脚本l脚本
     *
     * @return
     */
    Result execSQL();


    /**
     * 删除本地redis中所有的key
     *
     * @return
     */
    Result delRedisKeys();

    Result cleanData();

}
