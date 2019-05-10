package ioiobagiety.service.impl;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.service.EmailReminderService;
import ioiobagiety.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class BasicEmailReminderService implements EmailReminderService {

    @Autowired
    private EmailService emailService;

    public void addReminder (Date date, AppUser user, Lesson lesson) {
        new Timer().schedule(new EmailReminder(user, lesson), date);
    }

    @AllArgsConstructor
    private class EmailReminder extends TimerTask {
        AppUser user;
        Lesson lesson;

        @Override
        public void run () {
            switch (user.getUserType()) {
                case lecturer:
                    emailService.sendSimpleMessage(user.getEmail(), "Lesson Reminder", "REMINDING LECTURER");
                    break;
                case student:
                    emailService.sendSimpleMessage(user.getEmail(), "Lesson Reminder", "REMINDING");
                    break;
                default:
                    break;
            }
        }

    }

}
