package com.dhlk.service;

import com.dhlk.domain.SystemStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SystemStatusService extends Remote{
    SystemStatus getRuntime() throws RemoteException;//内存 M 网络 kb/s
}
