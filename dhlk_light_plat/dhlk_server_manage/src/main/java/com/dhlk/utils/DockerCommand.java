package com.dhlk.utils;

/**
 * @program: server
 * @description: docker命令常量
 * @author: wqiang
 * @create: 2020-07-07 14:45
 **/
public class DockerCommand {


    //查看所有服务
    public static final String DOCKER_ALL_SERVER_CMD = "docker ps -a";
    //删除服务
    public static final String DOCKER_DELETE_SERVER_CMD = "docker rm ";
    //停止服务
    public static final String DOCKER_STOP_SERVER_CMD = "docker stop ";
    //开启服务
    public static final String DOCKER_START_SERVER_CMD = "docker start ";
    //根据名称查询服务
    public static final String SEARCH_SERVER_BY_NAME_COMMAND="docker ps -aq --filter name=";
    //更具名称运行服务
    public static final String RUN_SERVER_BY_NAME_COMMAND="docker run -di --name=";
    //解释脚本
    public static final String BIN_BASH=" /bin/bash";
    //加载镜像到本地仓库
    public static final String LOAD_MIRROR_TO_LOCAL_COMMAND="docker load --input ";


}
