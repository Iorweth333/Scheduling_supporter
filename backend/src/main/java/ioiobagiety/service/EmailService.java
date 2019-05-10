package ioiobagiety.service;

import org.springframework.mail.MailException;

import javax.mail.MessagingException;
import java.io.File;

public interface EmailService {
    void sendSimpleMessage (String to,
                            String subject,
                            String text) throws MailException;

    void sendMessageWithAttachment (String to,
                                    String subject,
                                    String text,
                                    File attachment) throws MessagingException;
}
