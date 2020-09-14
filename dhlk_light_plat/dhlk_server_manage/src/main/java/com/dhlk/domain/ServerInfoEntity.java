package com.dhlk.domain;

import lombok.Data;

/**
 * @program: server
 * @description: 添加服务实体
 * @author: wqiang
 * @create: 2020-07-07 14:09
 **/
@Data
public class ServerInfoEntity {

    private String serverName;  //服务名称
    private String flag;           //是否上传文件flag  0不上传  1上传
    private String port;        //端口号
    private String mirrorName;//镜像名称
    private String startCommand;//启动命令
    private String fileAdress;  //上传文件的地址
    private String containerId; //服务ID


}
