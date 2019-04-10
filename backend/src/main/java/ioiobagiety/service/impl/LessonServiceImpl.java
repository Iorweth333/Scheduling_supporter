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
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public Lesson get (Long id) {
        return lessonRepository.findById(id)
                               .orElseThrow(() -> new ResourceNotFoundException("Lesson"));
    }

    @Transactional
    public List<Lesson> getAll () {
        List<Lesson> lessons = lessonRepository.findAll();
        if (lessons.size() > 0) {
            return lessons;
        } else {
            throw new ResourceNotFoundException("Lesson");
        }
    }

    @Transactional
    public Lesson create (Lesson lesson) {
        return lessonRepository.save(lesson);
    }
}