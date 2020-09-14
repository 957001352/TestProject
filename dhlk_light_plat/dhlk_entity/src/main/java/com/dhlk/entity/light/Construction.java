package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @author xkliu
 * @date 2020/6/3
 * <p>
 * 施工信息实体类
 */
@Data
@ApiModel(value = "construction", description = "施工信息")
public class Construction implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值", hidden = true)
    private Integer id;

    /**
     * 签约日期
     */
    @ApiModelProperty(value = "签约日期")
    private String signDate;

    /**
     * 商务人员
     */
    @ApiModelProperty(value = "商务人员")
    private String businessPeople;

    /**
     * 购入Pack 数量
     */
    @ApiModelProperty(value = "购入Pack 数量")
    private Integer packCount;

    /**
     * 安装开始日期
     */
    @ApiModelProperty(value = "安装开始日期")
    private String startDate;

    /**
     * 安装结束日期
     */
    @ApiModelProperty(value = "安装结束日期")
    private String endDate;

    /**
     * 实施人员
     */
    @ApiModelProperty(value = "实施人员")
    private String implPeople;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id",hidden = true)
    private Integer tenantId;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", hidden = true)
    private String createTime;

    @ApiModelProperty(hidden = true)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getBusinessPeople() {
        return businessPeople;
    }

    public void setBusinessPeople(String businessPeople) {
        this.businessPeople = businessPeople;
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImplPeople() {
        return implPeople;
    }

    public void setImplPeople(String implPeople) {
        this.implPeople = implPeople;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
