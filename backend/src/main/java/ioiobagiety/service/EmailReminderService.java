package ioiobagiety.service;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;

import java.util.Date;

public interface EmailReminderService {

    void addReminder(Date date, AppUser user, Lesson lesson);
}
