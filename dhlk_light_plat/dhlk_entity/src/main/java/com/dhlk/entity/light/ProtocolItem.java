package com.dhlk.entity.light;


import lombok.Data;

/**
* @Description:    协议条款实体类
* @Author:         gchen
* @CreateDate:     2020/5/28 17:24
*/
@Data
public class ProtocolItem {
    /** 包头 */
    private String packHead;
    /** 协议版本 */
    private String version;
    /** 寻址地址 */
    private String pointType;
    /** 组ID */
    private String categoryId;
    /** id */
    private String id;
    /** 设备类型 */
    private String deviceType;
    /** 命令类型 */
    private String orderType;
    /** 预留2*/
    private String reserved;
    /** 数据长度 */
    private String dataLen;
    /** 数据 */
    private String data;
    /** 校验位 */
    private String crc;
    /** 包尾 */
    private String packTail;

    @Override
    public String toString() {
        return packHead +
                version +
                pointType+
                categoryId +
                id +
                deviceType +
                orderType +
                reserved +
                dataLen +
                data +
                crc +
                packTail;
    }
}
