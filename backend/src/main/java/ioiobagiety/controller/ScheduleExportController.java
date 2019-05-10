package ioiobagiety.controller;

import com.google.gson.Gson;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleExportController {
    private Logger logger = LoggerFactory.getLogger(ScheduleExportController.class);

    @Autowired
    private LessonService lessonService;

    @Autowired
    private Gson gson;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getLessons() {
        List<Lesson> lessons = lessonService.getAll();

        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> getFromName(@PathVariable("name") String name) {
        List<Lesson> lessons = lessonService.get(name);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
        }
    }
}