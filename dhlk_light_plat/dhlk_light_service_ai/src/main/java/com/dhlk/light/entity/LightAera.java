package com.dhlk.light.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:14:51
 * @Description:
 */
@Data
public class LightAera {
    /**
     * Id
     */
    private String id;

    /**
     * 区域名称
     */
    @Length(min = 2, max = 50, message = "区域名称,最大长度为2-50位的中文字符")
    private String area;

    /**
     * 租户Id
     */
    private Integer tenantId;

    /**
     * 图纸地址
     */
    @Length(min = 2, max = 200, message = "图纸地址,最大长度为2-200位的英文字符串")
    private String imagePath;
}
