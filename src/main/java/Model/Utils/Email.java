package Model.Utils;

import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class Email {
    private static String username;
    private static String password;

    static {
        try {
            // Load properties từ src/main/resources/db.properties
            Properties props = new Properties();
            InputStream input = Email.class.getClassLoader().getResourceAsStream("email.properties");

            if (input == null) {
                throw new RuntimeException("Không tìm thấy file email.properties trong resources/");
            }

            props.load(input);

            username = props.getProperty("email.username");
            password = props.getProperty("email.password");

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin email", e);
        }
    }

    // Hàm gửi mail
    public static void send(String email, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            message.setContent(body, "text/plain; charset=UTF-8");

            Transport.send(message);
            System.out.println("Đã gửi mail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
