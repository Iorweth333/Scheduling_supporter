package ioiobagiety.service;

import javax.mail.MessagingException;
import java.io.File;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text) throws MessagingException;

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   File attachment) throws MessagingException;
}
