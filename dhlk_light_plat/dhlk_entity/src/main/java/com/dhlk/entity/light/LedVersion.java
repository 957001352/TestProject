package com.dhlk.entity.light;/**
 * @创建人 wangq
 * @创建时间 2020/6/30
 * @描述
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: dhlk.light.plat
 *
 * @description: 发送给MQTT的灯版本信息
 *
 * @author: wqiang
 *
 * @create: 2020-06-30 10:44
 **/

@Data
public class LedVersion {
    private String path;

    public LedVersion() {
    }

    public LedVersion(String path) {
        this.path = path;
    }
}
