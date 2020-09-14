package com.dhlk.domain;

import lombok.Data;

/**
 * @program: server
 * @description:
 * @author: wqiang
 * @create: 2020-07-09 08:58
 **/
@Data
public class ServerLogEntity {

    private String serverName;  //服务名称
    private String action;         //动作 0关闭 1启动
    private String command;     //命令
    private String errorLog;    //错误日志
    private String dateTime;    //时间
}
