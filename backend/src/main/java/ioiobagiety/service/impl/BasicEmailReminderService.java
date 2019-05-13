package ioiobagiety.service.impl;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.model.user.UserType;
import ioiobagiety.service.EmailReminderService;
import ioiobagiety.service.EmailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class BasicEmailReminderService implements EmailReminderService {

    private static final Logger logger = LoggerFactory.getLogger(BasicEmailReminderService.class);

    @Autowired
    private EmailService emailService;

    public void addReminder (Date date, AppUser user, Lesson lesson) {
        logger.info("Reminder scheduled for " + date);
        new Timer().schedule(new EmailReminder(user, lesson), date);
    }

    @AllArgsConstructor
    private class EmailReminder extends TimerTask {
        AppUser user;
        Lesson lesson;

        @Override
        public void run () {
            String msg = buildText();
            emailService.sendSimpleMessage(user.getEmail(), "Lesson Reminder", msg);
            logger.info("Email send to " + user.getEmail());
        }

        private String buildText () {
            StringBuilder text = new StringBuilder();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            text.append("Subject: ").append(lesson.getSubject().getName())
                .append("\nClassroom: ").append(lesson.getClassroom().getNumber())
                .append(" ").append(lesson.getClassroom().getBuilding())
                .append("\nDate: ").append(dt.format(lesson.getDate()))
                .append("\nTime: ").append(lesson.getStartsAt()).append(" - ").append(lesson.getEndsAt())
                .append("\nLecturer: ").append(lesson.getLecturer().getName())
                .append(" ").append(lesson.getLecturer().getSurname());
            if (user.getUserType() == UserType.lecturer) {
                text.append("\n\nStudents: ");
                for (AppUser student : lesson.getStudentsGroup().getStudents()) {
                    text.append("\n").append(student.getName()).append(" ").append(student.getSurname());
                }
            }

            return text.toString();
        }

    }

}
