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

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class BasicEmailReminderService implements EmailReminderService {

    private static final Logger logger = LoggerFactory.getLogger(BasicEmailReminderService.class);

    @Autowired
    private EmailService emailService;

    public void addReminder(Date date, AppUser user, Lesson lesson) {
        logger.info("Reminder scheduled for " + date);
        new Timer().schedule(new EmailReminder(user, lesson), date);
    }

    @AllArgsConstructor
    private class EmailReminder extends TimerTask {
        AppUser user;
        Lesson lesson;

        @Override
        public void run() {
            String msg = buildText();
            try {
                emailService.sendSimpleMessage(user.getEmail(), "Lesson Reminder", msg);
            } catch (MessagingException e) {
                logger.info("Email send to " + user.getEmail());
            }
        }

        private String buildText() {
            StringBuilder text = new StringBuilder();

            appendSubject(text);
            appendClassroom(text);
            appendDate(text);
            appendTime(text);
            appendLecturer(text);

            if (user.getUserType() == UserType.lecturer) {
                appendStudents(text);
            }

            return text.toString();
        }

        private void appendStudents(StringBuilder text) {
            text.append("\nStudents: \n");
            for (AppUser student : lesson.getStudentsGroup().getStudents()) {
                text.append(student.getName()).append(" ").append(student.getSurname()).append("\n");
            }
        }

        private void appendLecturer(StringBuilder text) {
            text.append("Lecturer: ").append(lesson.getLecturer().getName())
                    .append(" ").append(lesson.getLecturer().getSurname()).append("\n");
        }

        private void appendTime(StringBuilder text) {
            text.append("Time: ").append(lesson.getStartsAt()).append(" - ").append(lesson.getEndsAt()).append("\n");
        }

        private void appendDate(StringBuilder text) {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            text.append("Date: ").append(dt.format(lesson.getDate())).append("\n");
        }

        private void appendClassroom(StringBuilder text) {
            text.append("Classroom: ").append(lesson.getClassroom().getNumber()).append("\n");
        }

        private void appendSubject(StringBuilder text) {
            text.append("Subject: ").append(lesson.getSubject().getName())
                    .append(" ").append(lesson.getClassroom().getBuilding()).append("\n");
        }

    }

}
