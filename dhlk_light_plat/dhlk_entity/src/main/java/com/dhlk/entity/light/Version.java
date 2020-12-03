package com.dhlk.entity.light;

import lombok.Data;
/**
* @Description:    灯的版本
* @Author:         gchen
* @CreateDate:     2020/7/20 15:14
*/
@Data
public class Version<T> {
    private T version;

    public Version() {
    }

    public Version(T version) {
        this.version = version;
    }
}
