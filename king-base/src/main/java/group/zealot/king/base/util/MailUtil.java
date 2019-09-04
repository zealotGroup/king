package group.zealot.king.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Properties;

public class MailUtil {
    public static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static final String emailServer = "13.114.100.99";
    private static final String loginName = "test@zealot.group";
    private static final String password = "1994728wawT";
    private static final String server = "test@zealot.group";
    private static final String port = "30";
    private static final String auth = "true";

    public static void main(String[] args) {
        String emailTo = "19921160125@189.cn";
        String subject = "主题";
        String context = "内容";
        send(emailTo, subject, context);
    }

    /**
     * @param mailTo  收信人地址
     * @param subject 邮件标题
     * @param context 邮件正文
     * @param files   多个附件
     */
    public static boolean send(String mailTo, String subject, String context, File... files) {

        Transport transport = null;
        try {
            Session session = getSession();
            transport = getTransport(session);
            Multipart multipart = getMultipart(context);
            if (files != null) {
                for (File file : files) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }
            MimeMessage message = getMimeMessage(session, multipart, mailTo, subject);
            transport.sendMessage(message, message.getAllRecipients());
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
            return false;
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    logger.error("发送邮件失败，关闭transport异常", e);
                }
            }
        }
    }

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailServer);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        return Session.getInstance(props);
    }

    private static Transport getTransport(Session session) throws MessagingException {
        Transport transport = session.getTransport("smtp");
        transport.connect(emailServer, loginName, password);
        return transport;
    }

    private static MimeMessage getMimeMessage(Session session, Multipart multipart, String mailTo, String subject)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = new MimeMessage(session);

        // 设置自定义发件人昵称
        message.setFrom(new InternetAddress(MimeUtility.encodeText("车联网服务通知") + "<" + server + ">"));
        InternetAddress[] address = InternetAddress.parse(mailTo);
        message.setRecipients(Message.RecipientType.TO, address);
        message.setSubject(subject);
        // 设置邮件发送时间，将来要和服务器时间互转
        Calendar clientCal = Calendar.getInstance();
        message.setSentDate(clientCal.getTime());
        message.setContent(multipart);
        message.saveChanges();
        return message;
    }

    private static Multipart getMultipart(String context) throws MessagingException {
        Multipart multipart = new MimeMultipart();
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(context, "text/html;charset=UTF-8");
        multipart.addBodyPart(bodyPart);
        return multipart;
    }
}