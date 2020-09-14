package com.dhlk.entity.tb;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class TbProductDevices implements Serializable {
    private Id id;
    private String name;
    private String type;
    private String label;
    //tb属性
    private AdditionalInfo additionalInfo;

    public TbProductDevices() {
    }
    //保存时使用
    public TbProductDevices(String name, String type, String label, AdditionalInfo additionalInfo) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.additionalInfo = additionalInfo;
    }
    //更新时使用
    public TbProductDevices(Id id, String name, String type, String label, AdditionalInfo additionalInfo) {
        this(name, type, label,additionalInfo);
        this.id = id;
    }

}
