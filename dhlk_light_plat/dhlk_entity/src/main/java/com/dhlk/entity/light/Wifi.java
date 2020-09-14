package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Data
@ApiModel(value = "wifi", description = "Wifi")
public class Wifi {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值", hidden = true)
    private Integer id;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ip;

    /**
     * wifi密码
     */
    @ApiModelProperty(value = "wifi密码")
    private String password;

    /**
     * Wifi 模块选择:1byte(0:WG219 模 块, 1:E103-W01 模块, 2:汉枫 HF_LPD130 模块)
     */
    @ApiModelProperty(value = "Wifi 模块选择")
    private Integer wf_dev;

    /**
     *1byte(1:2.4G, 2:5G, 3:2.4G 和 5G)
     */
    @ApiModelProperty(value = "1byte(1:2.4G, 2:5G, 3:2.4G 和 5G)")
    private Integer wf_mode;
    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id", hidden = true)
    private Integer tenantId;

    /**
     * 灯sn
     */
    @ApiModelProperty(value = "灯sn")
    private String sn;

    /**
     * ssid
     */
    @ApiModelProperty(value = "ssid")
    private String ssid;

    /**
     * 类型 ,0灯 , 1开关
     */
    @ApiModelProperty(value = "类型")
    private String type;

}
