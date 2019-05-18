package ioiobagiety.service;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classroom.Classroom;

import java.util.List;

public interface ClassroomService {
    Classroom create(Classroom classroom);

    Classroom get(Long id) throws ResourceNotFoundException;

    List<Classroom> getAll() throws ResourceNotFoundException;
}
