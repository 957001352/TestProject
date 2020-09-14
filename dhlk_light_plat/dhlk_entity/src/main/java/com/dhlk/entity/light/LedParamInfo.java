package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
* @Description:    灯的参数状态信息
* @Author:         gchen
* @CreateDate:     2020/7/17 11:52
*/
@Data
@ApiModel(value="ledParamInfo",description="灯参数状态信息")
public class LedParamInfo {
    @ApiModelProperty(value = "新增为空/修改传值")
    private Integer id;
    @ApiModelProperty(value = "SN")
    private String sn;
    @ApiModelProperty(value = "光感开关")
    private Integer light_on_off;
    @ApiModelProperty(value = "照度上限值")
    private Integer illumi_top;
    @ApiModelProperty(value = "上限值最低亮度")
    private Integer illumi_top_min;
    @ApiModelProperty(value = "照度下限值")
    private Integer illumi_flr;
    @ApiModelProperty(value = "下限值最高亮度")
    private Integer illumi_flr_max;
    @ApiModelProperty(value = "人感开关")
    private Integer people_on_off;
    @ApiModelProperty(value = "延迟时间")
    private Integer trig_delay_tm;
    @ApiModelProperty(value = "渐变时间")
    private Integer n_ramp_tm;
    @ApiModelProperty(value = "最低照度")
    private Integer minval;
    @ApiModelProperty(value = "最高照度")
    private Integer maxval;
    @ApiModelProperty(value = "SSID")
    private String ssid;
    @ApiModelProperty(value = "网络IP")
    private String ip;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "wifi模块")
    private Integer wf_dev;
    @ApiModelProperty(value = "射频范围")
    private Integer wf_mode;
    @ApiModelProperty(value = "固件版本")
    private String version;

    @ApiModelProperty(value = "开关名称集合")
    private String switchNames;
    @ApiModelProperty(value = "开关通道")
    private Integer group_id;
    @ApiModelProperty(value = "别名")
    private String alias;



    public LedParamInfo() {
    }

    public LedParamInfo(String version) {
        this.version = version;
    }

    public LedParamInfo(IntensityInfo intensityInfo) {
        this.light_on_off = intensityInfo.getOn_off();
        this.illumi_top = intensityInfo.getIllumi_top();
        this.illumi_top_min = intensityInfo.getIllumi_top_min();
        this.illumi_flr = intensityInfo.getIllumi_flr();
        this.illumi_flr_max = intensityInfo.getIllumi_flr_max();
    }

    public LedParamInfo(PeopleFeelInfo peopleFeelInfo) {
        this.people_on_off = peopleFeelInfo.getOn_off();
        this.trig_delay_tm = peopleFeelInfo.getTrig_delay_tm();
        this.n_ramp_tm = peopleFeelInfo.getN_ramp_tm();
        this.minval = peopleFeelInfo.getMinvalue();
        this.maxval = peopleFeelInfo.getMaxvalue();
    }

    public LedParamInfo(LedWifiInfo ledWifiInfo) {
        this.ssid = ledWifiInfo.getSsid();
        this.ip = ledWifiInfo.getIp();
        this.password = ledWifiInfo.getPassword();
        this.wf_dev = ledWifiInfo.getWf_dev();
        this.wf_mode = ledWifiInfo.getWf_mode();
    }

}
