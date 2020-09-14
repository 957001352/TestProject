package com.dhlk.entity.tb.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceCredentials implements Serializable {
    private Id id;
    private DeviceId deviceId;
    private String credentialsType;
    private String credentialsId;
}
