package ioiobagiety.service;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;

import java.util.List;

public interface LessonService {
    Lesson get(Long id) throws ResourceNotFoundException;

    Lesson get(String name) throws ResourceNotFoundException;

    List<Lesson> getAll() throws ResourceNotFoundException;
}
