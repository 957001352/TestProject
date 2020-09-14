package com.dhlk.entity.hive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/18
 */
@Data
public class MetaTable implements Serializable {
    @ApiModelProperty(hidden = true)
    private Integer id;
    private String tableEnname;//数据表名称
    private String tableCnname;//数据表说明
    @ApiModelProperty(hidden = true)
    private String createTime;
    @ApiModelProperty(hidden = true)
    private Integer status;//0正常 2删除
    @ApiModelProperty(hidden = true)
    private List<MetaTableColumn> columnList;
}