package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.util.List;

@Data
public class MonitorReport {

    private List<MonitorDetail> reportList;

    private String title;

}