package com.dhlk.impls;

import lombok.Data;

/**
 * Content: 短信类，具体字段需要根据短信平台的要求，修改
 * Author:jpdong
 * Date:2020/3/3
 */
@Data
public class SMS {
    private String officeID;
    private String officeToken;
    private String smsURL;
    private String mobize;
    private String smsInfo;
    private String err;
}
