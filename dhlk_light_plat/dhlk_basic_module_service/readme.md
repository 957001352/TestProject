# 基本服务模块

* 组织管理(例如:工厂、车间、班组...)
* 角色管理
* 权限管理

* 登录服务
* 注销服务
* 权限服务
* 密码服务


tb保存或更新设备格式

    保存到tb的数据格式
    {
        "name": "dhlk111",
        "type": "dhlk222",
        "label": "dhlk333",
        "additionalInfo": {
            "gateway": true,
            "description": "dhlk444"
        }
    }
    更新到tb的数据格式
    {
        "id": {
            "entityType": "DEVICE",
            "id": "acdefe50-6da8-11ea-8392-6dbee2348266"
        },
        "additionalInfo": {
            "gateway": true,
            "description": "dhlk444"
        },
        "name": "机器lllvvv",
        "type": "dhlkvvv",
        "label": "002lllvvv"
    }