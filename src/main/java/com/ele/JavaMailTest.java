package com.ele;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * javaMail发送邮件
 *
 * @author oukailiang
 * @create 2016-08-24 下午6:38
 */

public class JavaMailTest {
    private static final String SEND_EMAIL = "a4print@163.com";

    private static final String EMAIL_PASSWORD = "A4prinT1";

    /**
     * smtp.sina.com
     */
    private static final String EMAIL_HOST = "smtp.163.com";

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        prop.setProperty("mail.host", EMAIL_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(EMAIL_HOST, SEND_EMAIL, EMAIL_PASSWORD);
        //4、创建邮件
        Message message = createSimpleMail(session);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @param session
     * @return
     * @throws Exception
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼
     */
    public static MimeMessage createSimpleMail(Session session)
            throws Exception {
        String registerId = "" + Math.random() * Math.random();
        String url = "http://a4print.cn/A4print/regesiter.do?registerId=" + registerId;
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(SEND_EMAIL));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("1024978310@qq.com"));
        //邮件的标题
        message.setSubject("验证A4print在线云打印注册邮箱");
        String content = "<h1 style='color:#58b2dc'>请确认你的邮箱地址！</h1><br/>点击下面的链接进行邮箱验证：<br/>" +
                "点击<a href='" + url + "'>" + url + "</a>完成注册";

        //邮件的文本内容
        message.setContent(content, "text/html;charset=utf-8");
        message.setSentDate(new Date());
        //返回创建好的邮件对象
        return message;
    }
}
