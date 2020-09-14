package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:    监控日志
 * @Author:         gchen
 * @CreateDate:     2020/7/16 10:17
 */
@Data
@ApiModel(value="monitoringLog",description="监控日志")
public class MonitoringLog {

    /** $column.columnComment */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** sn码 */
    @ApiModelProperty(value="sn码")
    private String sn;

    /** 设备状态 */
    @ApiModelProperty(value="设备状态")
    private Integer status;

    /** 添加时间 */
    @ApiModelProperty(value="添加时间")
    private String createTime;

    /** 租户 */
    @ApiModelProperty(value="租户id")
    private Integer tenantId;
}
