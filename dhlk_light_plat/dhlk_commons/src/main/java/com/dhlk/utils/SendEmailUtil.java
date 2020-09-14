package com.dhlk.utils;


import com.dhlk.exceptions.MyException;
import com.dhlk.systemconst.Const;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

/**
 * @author ykzhang
 * @date 2020/03/19 15:53
 */
public class SendEmailUtil {

    private static Session session;

    public static SendEmailUtil sendEmailUtil ;
    private static MimeMessage message;
    private Transport transport;


    public SendEmailUtil(){
        if(sendEmailUtil==null){
            sendEmailUtil = new SendEmailUtil();
        }
        sendEmailUtil.connect(Const.receiverMap,Const.subject,Const.content,Const.filePath);
    }

    /**
     * @param map ： 收件地址和发送类型 (key是邮箱地址)
     * @param subject : 邮件的主题
     * @param content ： 邮件的正文
     * @Param filePath :  附件地址
     * @throws MessagingException
     */
    public   void connect(Map <String, String> map, String subject, String content,String filePath){

        //1.配置发送邮件的属性
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.host",Const.SYSTEMESTMP); //设置协议主机
            properties.setProperty("mail.smtp.auth","true"); //设置smtp是否需要认证
            //2.根据属性获得一个会话
             session = Session.getInstance(properties);
            //3.设置会话是debug模式(会打印更多相关信息,生产环境可设为false)
            session.setDebug(true);
            //4.创建邮件主题信息对象
             message = new MimeMessage(session);
            //5.设置发件人
            message.setFrom(new InternetAddress(Const.SYTEMEMAIL));
            //6.设置邮件主题
            message.setSubject(subject);
            // 7 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setText(content);
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            // 附件部分
            messageBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            if(!StringUtils.isEmpty(filePath)){
                DataSource source = new FileDataSource(filePath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                // 处理附件名称中文（附带文件路径）乱码问题
                String fileName = filePath.split("\\\\")[filePath.split("\\\\").length-1];
                messageBodyPart.setFileName(MimeUtility.encodeText(fileName));
                multipart.addBodyPart(messageBodyPart);
            }
            // 发送完整消息
            message.setContent(multipart);
            //8.设置收件人: TO-发送    CC-抄送
            for (Map.Entry<String, String> me : map.entrySet()) {
                String email = me.getKey(); //邮件地址
                String type = me.getValue(); //邮件类型
                if ("to".equalsIgnoreCase(type)){
                    //发送
                    message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
                }else if ("cc".equalsIgnoreCase(type)){
                    //抄送
                    message.setRecipient(Message.RecipientType.CC,new InternetAddress(email));
                }
            }
        } catch (MessagingException e) {
            System.err.println("邮件发送异常");
            throw new MyException("3002",e.getMessage());

        } catch (UnsupportedEncodingException e) {
            System.err.println("文件编码异常");
            throw new MyException("3003",e.getMessage());
        }
    }
    /**
     * 发送邮件
     */
    public void send(){
        try {
            //提供指定的协议
            transport = session.getTransport("smtp");
            //设置发件人的信息
            transport.connect(Const.SYSTEMESTMP,Const.SYTEMEMAIL,Const.SYSTEMEAILPASSWORD);
            //发送邮件
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            System.err.println("邮件发送异常");
           throw new MyException("3002",e.getMessage());
        }
    }





}