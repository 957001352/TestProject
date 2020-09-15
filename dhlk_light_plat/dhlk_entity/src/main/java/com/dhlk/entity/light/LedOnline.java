package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 灯在线统计
 * @Author lpsong
 * @Date 2020/6/4
 */
@Data
@ApiModel(value="ledOnline",description="灯在线统计")
public class LedOnline {
    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;

    @ApiModelProperty(value = "灯sn码")
    private String ledSn;

    @ApiModelProperty(value = "创建时间(年月日时)")
    private String onlineHour;

    @ApiModelProperty(value = "在线时长(分钟)")
    private Integer onlineTime;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

}