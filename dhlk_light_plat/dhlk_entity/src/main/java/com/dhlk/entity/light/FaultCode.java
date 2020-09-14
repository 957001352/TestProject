package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:    故障代码
 * @Author:         gchen
 * @CreateDate:     2020/6/4 14:15
 */
@Data
@ApiModel(value="faultCode",description="故障代码")
public class FaultCode {
    /** $column.columnComment */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** 故障代码名称 */
    @ApiModelProperty(value="故障代码名称")
    private String name;

    /** 代码 */
    @ApiModelProperty(value="代码")
    private String code;

    /** 故障说明 */
    @ApiModelProperty(value="故障说明")
    private String content;

}
