package com.dhlk.entity.tb;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbTenant implements Serializable {
    private Id id;
    private String title;
    //tb属性
    private AdditionalInfo additionalInfo;

    public TbTenant() {
    }
    //保存时使用
    public TbTenant(String title,AdditionalInfo additionalInfo) {
        this.title = title;
        this.additionalInfo = additionalInfo;
    }
    //更新时使用
    public TbTenant(Id id, String title, AdditionalInfo additionalInfo) {
        this(title,additionalInfo);
        this.id = id;
    }

}
