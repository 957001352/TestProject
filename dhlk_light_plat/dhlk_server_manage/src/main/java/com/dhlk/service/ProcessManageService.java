package com.dhlk.service;

import com.dhlk.domain.Result;


public interface ProcessManageService {

    public Result getAllProcessInfo(String processName, String runStatus, String flag);


    public Result stopProcess(String pid);

    public Result startProcess(String processName);

    public Result serverUpdate(String processName, String fileAddress);

    public Result executeSQL(String sqlPath);

}
