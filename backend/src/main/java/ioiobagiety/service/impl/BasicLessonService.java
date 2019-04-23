package ioiobagiety.service.impl;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.repository.LessonRepository;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BasicLessonService implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public Lesson get(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        return lesson;
    }

    @Transactional
    public Lesson get(String name) {
        Lesson lesson = lessonRepository.findByScheduleName(name).orElseThrow(() -> new ResourceNotFoundException("Lesson not Found"));
        return lesson;
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
}
