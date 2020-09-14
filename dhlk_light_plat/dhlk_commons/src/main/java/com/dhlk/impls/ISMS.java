package com.dhlk.impls;

/**
 * Content:短信操作类,具体根据短信平台的要求，修改
 * Author:jpdong
 * Date:2020/3/3
 */
public interface ISMS {
    /**
     * 短信发送
     *
     * @param sms         短信息类
     * @param smsCallBack callback
     */
    void sendSMS(SMS sms, ISMSCallBack smsCallBack);

    /**
     * 短信收取
     *
     * @param sms         短信息类
     * @param smsCallBack callback
     */
    SMS getSMS(SMS sms, ISMSCallBack smsCallBack);
}
