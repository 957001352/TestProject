package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 智慧照明统计
 * @Author lpsong
 * @Date 2020/6/8
 */
public interface LightQueryService {
    /**
    *  照明设备安装情况查询
     * @param
    * @return 
    */
    Result ledIntallQuery(String province);

    /**
     *  按省份统计安灯查询
     * @param
     * @return
     */
    Result provinceQuery(String province);


    /**
     *  最新购买企业查询
     * @param
     * @return
     */
    Result lastCompanyQuery(String province, Integer limit);

    /**
     * 查询节能最高的企业（默认前10条）
     * @param province
     * @param limit
     * @return
     */
    Result energyComRanking(String province, Integer limit);

    /**
     * 节约碳排放
     * @return
     */
    Result thriftCarbonEmission(String province);

}