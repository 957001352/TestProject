package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: dhlk.light.plat
 * @description: 图表统计数据实体
 * @author: wqiang
 * @create: 2020-07-28 09:58
 **/
@Data
@ApiModel(value="GraphicStatistics",description="图标统计实体")
public class GraphicStatistics {

    @ApiModelProperty(value="今日能耗")
    private BigDecimal todayEnergy;

    @ApiModelProperty(value="昨日能耗")
    private BigDecimal yesterdayEnergy;

    @ApiModelProperty(value="累计能耗")
    private BigDecimal lightEnergyTotal;

    @ApiModelProperty(value="灯总数")
    private Integer lightTotal;

    @ApiModelProperty(value="灯在线数")
    private Integer lightOnlineCount;
}
