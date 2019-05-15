package ioiobagiety.service.impl;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.repository.LessonRepository;
import ioiobagiety.repository.StudentsGroupRepository;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BasicLessonService implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentsGroupRepository studentsGroupRepository;

    @Transactional
    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson getLesson(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }

    @Transactional
    public List<Lesson> getLessonsFromScheduleName(String name) {
        List<Lesson> lessons = lessonRepository.findByScheduleName(name);
        if (lessons.size() > 0) {
            return lessons;
        } else {
            throw new ResourceNotFoundException("No lessons found");
        }
    }

    @Transactional
    public List<Lesson> getAll() {
        List<Lesson> lessons = lessonRepository.findAll();
        if (lessons.size() > 0) {
            return lessons;
        } else {
            throw new ResourceNotFoundException("No lessons found");
        }
    }

    @Transactional
    public List<Lesson> getLessonsFromGroupName(String name) throws ResourceNotFoundException {
        List<Lesson> lessons = lessonRepository.findByStudentsGroup(studentsGroupRepository.findByName(name));
        if (lessons.size() > 0) {
            return lessons;
        } else {
            throw new ResourceNotFoundException("No lessons found for group: " + name);
        }
    }
}
