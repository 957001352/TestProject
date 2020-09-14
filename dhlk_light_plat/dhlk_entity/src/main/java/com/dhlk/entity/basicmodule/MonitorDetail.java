package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.util.Date;

@Data
public class MonitorDetail {

    private String tenantName;

    private String totalVal;

    private Date endTime;

    private String province;

    private String dataType;

    private Integer topN = 10;

}