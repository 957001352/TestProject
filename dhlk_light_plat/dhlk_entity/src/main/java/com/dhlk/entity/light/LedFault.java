package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:    故障信息上报
 * @Author:         gchen
 * @CreateDate:     2020/6/10 10:36
 */
@Data
@ApiModel(value="ledFault",description="故障信息上报")
public class LedFault {
    /** $column.columnComment */
    @ApiModelProperty(value="新增为空")
    private Integer id;

    /** 照明设备 */
    @ApiModelProperty(value="照明设备")
    private String ledSn;

    /** 故障代码 */
    @ApiModelProperty(value="故障代码")
    private String faultCode;

    /** 设备类型 1-灯/2-人感/3-光感/4-开关 */
    @ApiModelProperty(value="设备类型 1-灯/2-人感/3-光感/4-开关")
    private Integer type;


    /** 故障代码对象 */
    @ApiModelProperty(value="故障代码对象")
    private FaultCode faultObject;

    public LedFault(){

    }
    public LedFault(String ledSn, String faultCode, Integer type) {
        this.ledSn = ledSn;
        this.faultCode = faultCode;
        this.type = type;
    }
}
