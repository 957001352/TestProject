package com.dhlk.entity.tb;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AdditionalInfo implements Serializable {
   private boolean gateway;
   private String description;
}