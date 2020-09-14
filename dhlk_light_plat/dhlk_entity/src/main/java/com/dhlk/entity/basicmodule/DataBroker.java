package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息订阅mosquito管理
 */
@Data
public class DataBroker  implements Serializable {
    private Integer id;
    private String name;//mosquito名称
    private String ip;
    private String topicList;//mosquito主题
}
