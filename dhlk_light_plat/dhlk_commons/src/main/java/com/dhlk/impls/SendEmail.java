package com.dhlk.impls;

import lombok.Data;

/**
 * Content:
 * Author:jpdong
 * Date:2020/3/3
 */
@Data
public class SendEmail {
    private String title;
    private String content;
    private String fileName;
    private String sendEmailer;
    private String sendEmailPassword;
    private String reciverEmail;
}
