package com.dhlk.entity.light;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.utils.Convert;
import com.dhlk.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 灯版本信息
 * @Author wqiang
 * @Date 2020/6/30
 */
@Data
@ApiModel(value="ledVersionInfo",description="灯版本信息")
public class LedVersionInfo {
    @ApiModelProperty(value = "新增为空/修改传值")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "下载地址")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "更新人")
    private String updatePerson;

}