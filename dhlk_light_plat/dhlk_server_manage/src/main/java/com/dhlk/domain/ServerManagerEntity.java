package com.dhlk.domain;

import lombok.Data;

/**
 * @program: server
 * @description:
 * @author: wqiang
 * @create: 2020-07-03 16:59
 **/
@Data
public class ServerManagerEntity {

    private String containerId;
    private String image;       //镜像
    private String command;     //命令
    private String name;        //名称
    private String createTime;  //创建时间
    private String startTime;   //开始时间
    private String endTime;     //结束时间
    private String runTime;     //运行时长
    private int status;         //状态  开-关  0关1开
    private String statusDes;      //状态描述
    private String port;
}
