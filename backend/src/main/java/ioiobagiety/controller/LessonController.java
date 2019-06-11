package ioiobagiety.controller;

import com.google.gson.Gson;
import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public ResponseEntity<String> getLessons() {
        List<Lesson> lessons;
        try {
            lessons = lessonService.getAll();
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
    }

    @RequestMapping(value = "/lessons/{lessonId}", method = RequestMethod.GET)
    public ResponseEntity<String> getLesson(@PathVariable("lessonId") long lessonId) {
        Lesson lesson;
        try {
            lesson = lessonService.getLesson(lessonId);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(lesson), HttpStatus.OK);
    }
}
