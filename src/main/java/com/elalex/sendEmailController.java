package com.elalex;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;


public class sendEmailController
{

    public static void sendEmail( String fileName)
    {
    String from = "parisbakeryisr@gmail.com";
    String to = "secured128@gmail.com";
    String subject = "Important Message";
    String bodyText = "This is a important message with attachment.";

    // The attachment file name.
    String attachmentName = fileName;

    // Creates a Session with the following properties.
    Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
    Session session = Session.getDefaultInstance(props);
        session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");

        try {
        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);

        // Create an Internet mail msg.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // Set the email msg text.
        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText(bodyText);

        // Set the email attachment file
        FileDataSource fileDataSource = new FileDataSource(attachmentName);

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(fileDataSource));
        attachmentPart.setFileName(fileDataSource.getName());

        // Create Multipart E-Mail.
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);
        multipart.addBodyPart(attachmentPart);

        msg.setContent(multipart);

        // Send the msg. Don't forget to set the username and password
        // to authenticate to the mail server.
        Transport.send(msg, "parisbakeryisr", "pojhi9078");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
}
}
