package com.dhlk.entity.hive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/18
 */
@Data
public class MetaTableColumn implements Serializable {
    private Integer id;
    private String columnEnname;//列名
    private String columnCnname;//列说明
    private String dataType;//字段类型
    private Integer length;//字段长度
    private Integer tableId;//数据表
    @ApiModelProperty(hidden = true)
    private Integer status;//0正常 2删除
}