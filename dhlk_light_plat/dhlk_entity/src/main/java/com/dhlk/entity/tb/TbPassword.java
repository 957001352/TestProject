package com.dhlk.entity.tb;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbPassword implements Serializable {
    private String password;
    private String activateToken;

    public TbPassword() {
    }
    public TbPassword(String password, String activateToken) {
        this.password = password;
        this.activateToken = activateToken;
    }

}
