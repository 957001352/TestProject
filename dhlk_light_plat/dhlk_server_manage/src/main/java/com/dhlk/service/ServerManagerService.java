package com.dhlk.service;

import com.dhlk.domain.Result;
import com.dhlk.domain.ServerInfoEntity;

/**
 * docker业务逻辑层
 */
public interface ServerManagerService {


    public Result findServerManagerList(String name, int runStatus, Integer pageNum, Integer pageSize);

    public Result delete(String ids, String name);

    public Result startService(String id, String name);

    public Result stopService(String id, String name);

    public Result addOrUpdate(ServerInfoEntity serverInfoEntity);

    public Result findServerLog(String serverName, Integer pageNum, Integer pageSize);

    public Result findServerManagerLists(String name, int runStatus, Integer pageNum, Integer pageSize);

    public Result findServerManagerList(String name, int runStatus);

    public Result findClientInfo(String containerId);



}
