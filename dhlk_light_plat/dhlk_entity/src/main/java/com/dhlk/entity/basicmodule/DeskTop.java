package com.dhlk.entity.basicmodule;/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: dhlk.light.plat
 *
 * @description: 桌面菜单实体
 *
 * @author: wqiang
 *
 * @create: 2020-07-01 09:43
 **/
@Data
@ApiModel(value="DeskTop",description="桌面菜单")
public class DeskTop {

    @ApiModelProperty(value="新增为空",hidden = true)
    private Integer id;
    @ApiModelProperty(value="菜单ID")
    private Integer menuId;
    @ApiModelProperty(value="用户ID",hidden = true)
    private Integer userId;

    @ApiModelProperty(value="菜单父ID")
    private Integer parentId;

}
