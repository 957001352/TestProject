package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Description:    照明一体机
* @Author:         gchen
* @CreateDate:     2020/6/4 14:15
*/
@Data
@ApiModel(value="computer",description="照明一体机")
public class Computer {
    /** $column.columnComment */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** 名称 */
    @ApiModelProperty(value="一体机名称")
    private String name;

    /** sn码 */
    @ApiModelProperty(value="sn码")
    private String sn;

    /** ip地址 */
    @ApiModelProperty(value="ip地址")
    private String ip;

    /** 添加时间 */
    @ApiModelProperty(value="添加时间")
    private String createTime;

    /** 租户id */
    @ApiModelProperty(hidden = true)
    private Integer tenantId;
}
