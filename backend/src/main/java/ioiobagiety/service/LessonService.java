package ioiobagiety.service;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;

import java.util.List;

public interface LessonService {
    Lesson create(Lesson lesson);

    Lesson get(Long id) throws ResourceNotFoundException;

    List<Lesson> getByScheduleName(String name) throws ResourceNotFoundException;

    List<Lesson> getByClassroomId(Long id);

    List<Lesson> getAll() throws ResourceNotFoundException;
}
