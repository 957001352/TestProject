package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 光感
 */
@Data
@ApiModel(value = "intensity", description = "光感")
public class Intensity {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;


    /**
     * 光感开关
     */
    @ApiModelProperty(value = "光感开关")
    private Integer on_off;

    /**
     * 照度上限值
     */
    @ApiModelProperty(value = "照度上限值")
    private Float illumi_top;

    /**
     * 照度上限对应亮度最小值
     */
    @ApiModelProperty(value = "照度上限对应亮度最小值")
    private Float illumi_top_min;

    /**
     * 照度下限值
     */
    @ApiModelProperty(value = "照度下限值")
    private Float illumi_flr;

    /**
     * 照度下限对应亮度最大值
     */
    @ApiModelProperty(value = "照度下限对应亮度最大值")
    private Float illumi_flr_max;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id",hidden = true)
    private Integer tenantId;

    /**
     * 灯sn
     */
    @ApiModelProperty(value = "灯sn")
    private String sn;

    /**
     * 控制参数
     *
     */
    @ApiModelProperty(value = "灯sn",hidden = true)
    private Integer controlParam;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOn_off() {
        return on_off;
    }

    public void setOn_off(Integer on_off) {
        this.on_off = on_off;
    }

    public Float getIllumi_top() {
        return illumi_top;
    }

    public void setIllumi_top(Float illumi_top) {
        this.illumi_top = illumi_top;
    }

    public Float getIllumi_top_min() {
        return illumi_top_min;
    }

    public void setIllumi_top_min(Float illumi_top_min) {
        this.illumi_top_min = illumi_top_min;
    }

    public Float getIllumi_flr() {
        return illumi_flr;
    }

    public void setIllumi_flr(Float illumi_flr) {
        this.illumi_flr = illumi_flr;
    }

    public Float getIllumi_flr_max() {
        return illumi_flr_max;
    }

    public void setIllumi_flr_max(Float illumi_flr_max) {
        this.illumi_flr_max = illumi_flr_max;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getControlParam() {
        return controlParam;
    }

    public void setControlParam(Integer controlParam) {
        this.controlParam = controlParam;
    }
}
