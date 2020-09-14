package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设置报警规则
 */
@Data
@ApiModel(value = "redisRule", description = "设置报警规则")
public class AlarmRule {
    @ApiModelProperty(value = "cpu的报警规则")
    private RedisAlarmRule redisCpuRule;
    @ApiModelProperty(value = "内存的报警规则")
    private RedisAlarmRule redisMemoryRule;
    @ApiModelProperty(value = "磁盘的报警规则")
    private RedisAlarmRule redisDiskRule;

    public AlarmRule() {
    }

    public AlarmRule(RedisAlarmRule redisCpuRule, RedisAlarmRule redisMemoryRule, RedisAlarmRule redisDiskRule) {
        this.redisCpuRule = redisCpuRule;
        this.redisMemoryRule = redisMemoryRule;
        this.redisDiskRule = redisDiskRule;
    }
}
