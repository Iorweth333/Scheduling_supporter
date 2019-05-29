package ioiobagiety.controller;


import com.google.gson.Gson;
import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.service.ClassroomService;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private Gson gson;

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getClassrooms() {
        List<Classroom> classrooms;
        try {
            classrooms = classroomService.getAll();
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(classrooms), HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/{classroomId}", method = RequestMethod.GET)
    public ResponseEntity<String> getClassroom(@PathVariable("classroomId") long classroomId) {
        Classroom classroom;
        try {
            classroom = classroomService.get(classroomId);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(classroom), HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/{classroomId}/lessons", method = RequestMethod.GET)
    public ResponseEntity<String> getLessons(@PathVariable("classroomId") long classroomId) {
        List<Lesson> lessons;
        try {
            lessons = lessonService.getLessonsFromClassroomId(classroomId);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(lessons), HttpStatus.OK);
    }
}
