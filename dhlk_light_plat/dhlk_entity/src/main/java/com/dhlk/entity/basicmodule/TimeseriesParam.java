package com.dhlk.entity.basicmodule;

import lombok.Data;

@Data
public class TimeseriesParam {
    private Integer deviceId;
    private String keys;
    private Long startTs;
    private Long endTs;
    private Long interval;
    private Integer limit;
    private String agg;
}
