package com.dhlk.light.service;

import com.dhlk.light.util.Result;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:18
 * @Description:
 */
public interface KMeansLightService {
    Result findAll(String factoryCode);


    Result lightProportion(String factoryCode);

    Result focusSnAndArea(String factoryCode);
}
