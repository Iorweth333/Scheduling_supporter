package ioiobagiety.controller;

import com.google.gson.Gson;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleExportController {
    private Logger logger = LoggerFactory.getLogger(ScheduleExportController.class);

    @Autowired
    private LessonService lessonService;

    @Autowired
    private Gson gson;

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getLessons() {
        List<Lesson> lessons = lessonService.getAll();

        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
        }
    }

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> getFromScheduleName(@PathVariable("name") String name) {
        List<Lesson> lessons = lessonService.getLessonsFromScheduleName(name);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
        }
    }

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/lecturer/{surname}", method = RequestMethod.GET)
    public ResponseEntity<String> getFromLecturerSurname(@PathVariable("surname") String name) {
        List<Lesson> lessons = lessonService.getLessonsFromLecturerSurname(name);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
        }
    }

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/lecturer/{name}/{surname}", method = RequestMethod.GET)
    public ResponseEntity<String> getFromLecturerNameAndSurname(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        List<Lesson> lessons = lessonService.getLessonsFromLecturerNameAndSurname(name, surname);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
        }
    }
}
