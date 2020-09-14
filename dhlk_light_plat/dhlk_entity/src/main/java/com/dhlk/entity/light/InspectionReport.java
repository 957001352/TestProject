package com.dhlk.entity.light;

import com.dhlk.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告实体类
 */
@Data
@ApiModel(value = "inspectionReport", description = "验收报告")
public class InspectionReport implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(value = "Id", hidden = true)
    private Integer id;

    /**
     * 开关
     */
    @ApiModelProperty(value = "开关")
    private String off;
    /**
     * 开关
     */
    @ApiModelProperty(value = "开关")
    private String ons;
    /**
     * 调光
     */
    @ApiModelProperty(value = "调关")
    private String dimming;

    /**
     * 定时控制
     */
    @ApiModelProperty(value = "定时控制")
    private String timedControl;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id", hidden = true)
    private Integer tenantId;

    /**
     * sn
     */
    @ApiModelProperty(value = "sn", hidden = true)
    private String sn;

    /**
     * 区域
     */
    @ApiModelProperty(value = "区域", hidden = true)
    private String area;

    /**
     * 签约日期
     */
    @ApiModelProperty(value = "签约日期", hidden = true)
    private String signDate;

    /**
     * 施工完成日期
     */
    @ApiModelProperty(value = "施工完成日期", hidden = true)
    private String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getDimming() {
        return dimming;
    }

    public void setDimming(String dimming) {
        this.dimming = dimming;
    }

    public String getTimedControl() {
        return timedControl;
    }

    public void setTimedControl(String timedControl) {
        this.timedControl = timedControl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOns() {
        return ons;
    }

    public void setOns(String ons) {
        this.ons = ons;
    }

    public InspectionReport(String ons, String off, String dimming, String timedControl, String sn, Integer tenantId) {
        this.ons = ons;
        this.off = off;
        this.dimming = dimming;
        this.timedControl = timedControl;
        this.sn = sn;
        this.tenantId=tenantId;
        this.createTime= DateUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }
}
