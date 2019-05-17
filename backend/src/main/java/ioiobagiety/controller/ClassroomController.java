package ioiobagiety.controller;


import com.google.gson.Gson;
import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public ResponseEntity<String> getLessons() {
        Map<Classroom, List<Lesson>> lessonsInClassrooms;
        try {
            lessonsInClassrooms = classroomService.getLessonsInClassrooms();
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gson.toJson(lessonsInClassrooms), HttpStatus.OK);
    }
}
