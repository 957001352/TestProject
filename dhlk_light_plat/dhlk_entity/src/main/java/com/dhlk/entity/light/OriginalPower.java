package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业原照明功率维护实体类
 */
@Data
@ApiModel(value = "originalPower", description = "企业原照明功率维护")
public class OriginalPower implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值", hidden = true)
    private Integer id;

    /**
     * 灯数量
     */
    @ApiModelProperty(value = "灯数量")
    private Integer ledCount;

    /**
     * 灯功率
     */
    @ApiModelProperty(value = "灯功率")
    private Float ledPower;

    /**
     * 开灯时长小时
     */
    @ApiModelProperty(value = "开灯时长小时")
    private Float ledOpentime;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id", hidden = true)
    private Integer tenantId;

    /**
     * 预设亮度
     */
    @ApiModelProperty(value = "预设亮度", hidden = true)
    private Integer preBrightness;

    /**
     * 系统初始化时间
     */
    @ApiModelProperty(value = "系统初始化时间", hidden = true)
    private String systemRunTime;

    /**
     * 图标大小
     */
    @ApiModelProperty(value = "图标大小")
    private String iconSize;

    public String getSystemRunTime() {
        return systemRunTime;
    }

    public void setSystemRunTime(String systemRunTime) {
        this.systemRunTime = systemRunTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLedCount() {
        return ledCount;
    }

    public void setLedCount(Integer ledCount) {
        this.ledCount = ledCount;
    }

    public Float getLedPower() {
        return ledPower;
    }

    public void setLedPower(Float ledPower) {
        this.ledPower = ledPower;
    }

    public Float getLedOpentime() {
        return ledOpentime;
    }

    public void setLedOpentime(Float ledOpentime) {
        this.ledOpentime = ledOpentime;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getPreBrightness() {
        return preBrightness;
    }

    public void setPreBrightness(Integer preBrightness) {
        this.preBrightness = preBrightness;
    }

    public String getIconSize() {
        return iconSize;
    }

    public void setIconSize(String iconSize) {
        this.iconSize = iconSize;
    }
}
