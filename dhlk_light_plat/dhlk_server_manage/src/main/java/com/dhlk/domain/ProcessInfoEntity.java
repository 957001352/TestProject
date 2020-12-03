package com.dhlk.domain;

import lombok.Data;

/**
 * @program: dhlk_server_manage
 * @description:
 * @author: wqiang
 * @create: 2020-07-13 10:22
 **/
@Data
public class ProcessInfoEntity {

    private String  pid;          //进程ID
    private String  processName;  //进程名称
    private String  startTime;    //开始时间
    private String  runTime;      //运行时长
    private String  status = "0"; //0关闭 1开启
    private String  type;         //应用类型 1sql 2web 3other 4 front  5硬件服务 6只有开关，无上传升级功能 //3表示不升级，供前端控制

}
