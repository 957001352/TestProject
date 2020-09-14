package com.dhlk.entity.tb;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.basicmodule.Tenant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class TbTenantUser implements Serializable {
    private Id id;
    private String email;
    private String firstName;
    private String lastName;
    private String authority;
    private Id tenantId;


    public TbTenantUser() {
    }
    //保存时使用
    public TbTenantUser(Id tanentId,String email,String authority) {
        this.email = email;
        this.tenantId=tanentId;
        this.authority=authority;
    }
    //更新时使用
    public TbTenantUser(Id id, Id tanentId,String email, String authority) {
        this(tanentId,email,authority);
        this.id = id;
    }
}
