package ioiobagiety.service;

import ioiobagiety.exception.BadRequestException;
import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;

import java.util.List;

public interface LessonService {

    Lesson createLesson(Lesson lesson) throws BadRequestException;

    Lesson getLesson(Long id) throws ResourceNotFoundException;

    List<Lesson> getLessonsFromScheduleName(String name) throws ResourceNotFoundException;

    List<Lesson> getLessonsFromClassroomId(Long id);

    List<Lesson> getAll() throws ResourceNotFoundException;

    List<Lesson> getLessonsFromGroupName(String name) throws ResourceNotFoundException;
    
    List<Lesson> getLessonsFromLecturerSurname(String name) throws ResourceNotFoundException;

    List<Lesson> getLessonsFromLecturerNameAndSurname(String name, String surname) throws
            ResourceNotFoundException;
}
