package com.dhlk.entity.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * API分类
 */
@Data
public class ApiClassify implements Serializable {
    private Integer id;
    @NotEmpty(message = "分类名称不能为空")
    private String className;
    private Integer parentId;
    @ApiModelProperty(hidden = true)
    private List<ApiClassify> childList = new ArrayList<ApiClassify>();
}
