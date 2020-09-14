package com.dhlk.entity.light;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.utils.Convert;
import com.dhlk.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: dhlk.light.plat
 * @description: 本地人感数据统计
 * @author: wqiang
 * @create: 2020-07-21 15:33
 **/

@Data
@ApiModel(value="LocalPeopleFeelStatistics",description="本地人感数据统计")
public class LocalPeopleFeelStatistics {

    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;

    @ApiModelProperty(value = "灯sn码")
    private String ledSn;

    @ApiModelProperty(value = "统计时间",hidden = true)
    private String createTime;

    @ApiModelProperty(value = "人感状态 0无人 1有人",hidden = true)
    private Integer status;


    public LocalPeopleFeelStatistics() {
        this.createTime= Convert.formatDateTime(DateUtils.getLongCurrentTimeStamp(),"yyyy-MM-dd HH:mm:ss");
    }

}
