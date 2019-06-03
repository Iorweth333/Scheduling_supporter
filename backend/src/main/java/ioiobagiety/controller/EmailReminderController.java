package ioiobagiety.controller;

import com.google.gson.Gson;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.service.AppUserService;
import ioiobagiety.service.EmailReminderService;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class EmailReminderController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private EmailReminderService emailReminderService;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/remind", method = RequestMethod.POST)
    public ResponseEntity<String> remind(@RequestParam("date") String date,
                                         @RequestParam("user") String userId,
                                         @RequestParam("lesson") String lessonId) {
        Lesson lesson;
        AppUser user;
        Date reminderDate;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            reminderDate = dt.parse(date);
            user = appUserService.get(Long.parseLong(userId));
            lesson = lessonService.getLesson(Long.parseLong(lessonId));
        } catch (ParseException e) {
            return new ResponseEntity<>("Bad Date format [yyyy-MM-dd HH:mm]", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("User or Lesson not found", HttpStatus.NOT_FOUND);
        }
        emailReminderService.addReminder(reminderDate, user, lesson);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
