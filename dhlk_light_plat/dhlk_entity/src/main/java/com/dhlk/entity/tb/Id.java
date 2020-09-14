package com.dhlk.entity.tb;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Id implements Serializable {
    private String id;
    private String entityType;
}
