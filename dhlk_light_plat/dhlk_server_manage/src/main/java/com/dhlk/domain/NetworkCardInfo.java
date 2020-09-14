package com.dhlk.domain;

import lombok.Data;

/**
 * 设备网卡信息实体类
 */
@Data
public class NetworkCardInfo {
    private String nicName; //网卡名
    private String nicMACAddr; //MAC地址
    private String IPAddr;  //IP地址
    private long receiveTotal; //接收的总包裹数
    private long sendTotal; //发送的总包裹数
    private long receiveTotalError; // 接收的错误包数
    private long sendTotalError; //发送的错误包数
    private long receieveTotalThrow; //接收时丢弃的包数
    private long sendTotalThrow; //发送时丢弃的包数

    public NetworkCardInfo(String nicName, String nicMACAddr, String IPAddr, long receiveTotal, long sendTotal, long receiveTotalError, long sendTotalError, long receieveTotalThrow, long sendTotalThrow) {
        this.nicName = nicName;
        this.nicMACAddr = nicMACAddr;
        this.IPAddr = IPAddr;
        this.receiveTotal = receiveTotal;
        this.sendTotal = sendTotal;
        this.receiveTotalError = receiveTotalError;
        this.sendTotalError = sendTotalError;
        this.receieveTotalThrow = receieveTotalThrow;
        this.sendTotalThrow = sendTotalThrow;
    }

    public NetworkCardInfo() {
    }
}
