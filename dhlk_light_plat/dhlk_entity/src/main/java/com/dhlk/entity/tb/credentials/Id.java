package com.dhlk.entity.tb.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Id implements Serializable {
    private String id;//tb设备凭据类id(访问令牌id)
}
