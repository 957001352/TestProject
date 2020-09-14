package com.dhlk.impls;

/**
 * Content:
 * Author:jpdong
 * Date:2020/3/3
 */
public interface IEmail {
    public void sendEmail(SendEmail sendEmail,IEmailCallBack emailCallBack);
}
