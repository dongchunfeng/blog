package com.itzixue.blog.util;

import com.itzixue.blog.entity.Comment;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author Mr.Dong
 * @date 2020/5/27 17:10
 **/
public class SendEmailBlog extends Thread {

    //收件人信息
    private Comment comment;

    private String content;


    private String domain;

    //发件人信息
    private String From = "1013084647@qq.com";
    //发件人邮箱
    private String recipient = "1013084647@qq.com";
    //邮箱密码
    private String password = "xarlshuwxmmqbeed";
    //邮件发送的服务器
    private String host = "smtp.qq.com";


    public SendEmailBlog(String content, Comment comment){
        this.content = content;
        this.comment = comment;
        this.domain = "http://39.107.90.118:8081/blogs/"+comment.getBlog().getId();
    }

    @Override
    public void run() {
        try {
            Properties properties = new Properties();

            properties.setProperty("mail.host","smtp.qq.com");

            properties.setProperty("mail.transport.protocol","smtp");

            properties.setProperty("mail.smtp.auth","true");

            //QQ存在一个特性设置SSL加密
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            //创建一个session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(recipient,password);
                }
            });

            //开启debug模式
            session.setDebug(true);

            //获取连接对象
            Transport transport = null;
            try {
                transport = session.getTransport();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }

            //连接服务器
            transport.connect(host,From,password);


            //创建一个邮件发送对象
            MimeMessage mimeMessage = new MimeMessage(session);
            //邮件发送人
            mimeMessage.setFrom(new InternetAddress(recipient));

            //邮件接收人
            mimeMessage.setRecipient(javax.mail.Message.RecipientType.TO,new InternetAddress(comment.getEmail()));

            //邮件标题
            mimeMessage.setSubject("您在汤姆博客下的留言收到回复啦,请查收");

            //邮件内容
            mimeMessage.setContent("<p>评论回复:<span style='font-weight:600'> "+content+"</span></p><p>前往博客留言区: </p><a target='_blank' href='"+domain+"'>"+domain+"</a>","text/html;charset=UTF-8");

            //发送邮件
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

            transport.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
