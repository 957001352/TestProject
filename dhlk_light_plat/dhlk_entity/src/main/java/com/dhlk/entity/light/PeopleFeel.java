package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 人感实体类
 */
@Data
@ApiModel(value = "peopleFeel", description = "人感")
public class PeopleFeel {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;


    /**
     * 人感开关
     */
    @ApiModelProperty(value = "人感开关")
    private Integer on_off;

    /**
     * 触发延迟时间
     */
    @ApiModelProperty(value = "触发延迟时间")
    private Float trig_delay_tm;

    /**
     * 不触发渐变时间
     */
    @ApiModelProperty(value = "不触发渐变时间")
    private Float n_ramp_tm;

    /**
     * 人感目标最低亮度
     */
    @ApiModelProperty(value = "人感目标最低亮度")
    private Float minvalue;

    /**
     * 人感目标最高亮度
     */
    @ApiModelProperty(value = "人感目标最高亮度")
    private Float maxvalue;

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

    public Float getTrig_delay_tm() {
        return trig_delay_tm;
    }

    public void setTrig_delay_tm(Float trig_delay_tm) {
        this.trig_delay_tm = trig_delay_tm;
    }

    public Float getN_ramp_tm() {
        return n_ramp_tm;
    }

    public void setN_ramp_tm(Float n_ramp_tm) {
        this.n_ramp_tm = n_ramp_tm;
    }

    public Float getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(Float minvalue) {
        this.minvalue = minvalue;
    }

    public Float getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(Float maxvalue) {
        this.maxvalue = maxvalue;
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
}
