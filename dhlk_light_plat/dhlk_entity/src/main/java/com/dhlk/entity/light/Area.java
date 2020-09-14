package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 施工区域实体类
 */
@Data
@ApiModel(value = "area", description = "施工区域")
public class Area implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增",hidden = true)
    private String id;

    /**
     * 区域名称
     */
    @Length(min = 2, max = 50, message = "区域名称,最大长度为2-50位的中文字符")
    @ApiModelProperty(value = "区域名称")
    private String area;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id", hidden = true)
    private Integer tenantId;

    /**
     * 图纸地址
     */
    @Length(min = 2, max = 200, message = "图纸地址,最大长度为2-200位的英文字符串")
    @ApiModelProperty(value = "图纸地址")
    private String imagePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
