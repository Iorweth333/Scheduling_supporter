package ioiobagiety.service;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.classroom.Classroom;

import java.util.List;
import java.util.Map;

public interface ClassroomService {
    Classroom create(Classroom classroom);

    Classroom get(Long id) throws ResourceNotFoundException;

    List<Classroom> getAll() throws ResourceNotFoundException;

    Map<Classroom, List<Lesson>> getLessonsInClassrooms() throws ResourceNotFoundException;
}
