package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 存储的报警规则信息
 */
@Data
@ApiModel(value = "redisAlarmRule", description = "报警规则信息")
public class RedisAlarmRule {
    @ApiModelProperty(value = "统计周期  1分钟 5分钟  10分钟")
    private Integer cycle;
    @ApiModelProperty(value = "统计方法  1:平均值  2:最大值  3:最小值")
    private Integer way;
    @ApiModelProperty(value = "条件 1:大于 2:大于等于 3:等于 4:小于等于 5:小于")
    private Integer condition;
    @ApiModelProperty(value = "阈值")
    private Integer threshold;
}
