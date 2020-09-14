package com.dhlk.entity.tb.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DeviceId implements Serializable {
    private String id;//tb设备id
    private String entityType;
}
