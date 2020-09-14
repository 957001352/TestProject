package com.dhlk.entity.light;

import com.dhlk.utils.Convert;
import com.dhlk.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: dhlk.light.plat
 * @description: 本地人感数据统计
 * @author: wqiang
 * @create: 2020-07-21 15:33
 **/

@Data
@ApiModel(value="CloudPeopleFeelStatistics",description="云人感数据统计")
public class CloudPeopleFeelStatistics {

    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;

    @ApiModelProperty(value = "灯sn码")
    private String ledSn;

    @ApiModelProperty(value = "统计时间",hidden = true)
    private String createTime;

    @ApiModelProperty(value = "次数",hidden = true)
    private Integer number;


    public CloudPeopleFeelStatistics() {
        this.createTime= Convert.formatDateTime(DateUtils.getLongCurrentTimeStamp(),"yyyy-MM-dd hh:mm:ss");
    }

}
