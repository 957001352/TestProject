package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.SetNameCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 机构管理
 */
@Data
@ApiModel(value="org",description="机构对象")
public class Org implements Serializable {

    /** $column.columnComment */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** 机构编码 */
    @ApiModelProperty(value="机构编码")
    private String code;

    /** 机构名称 */
    @SetNameCheck(message = "请填写正确的机构名称，2-20位的汉字、数字和字母", maxLength = 20)
    @ApiModelProperty(value="机构名称",required = true)
    private String name;

    /** 父机构 */
    @ApiModelProperty(value="父机构id")
    private Integer parentId;

    /** 状态 0正常 2 删除 */
    @ApiModelProperty(value="状态 0正常 2 删除")
    private Integer status;

    /** 机构员工数量 */
    @ApiModelProperty(hidden = true)
    private int staffNum;

    /** 租户id */
    @ApiModelProperty(hidden = true)
    private Integer tenantId;

    /** 子集 */
    @ApiModelProperty(hidden = true)
    private String child;
}
