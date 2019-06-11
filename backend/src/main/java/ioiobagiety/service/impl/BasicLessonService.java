package ioiobagiety.service.impl;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.repository.AppUserRepository;
import ioiobagiety.repository.LessonRepository;
import ioiobagiety.repository.StudentsGroupRepository;
import ioiobagiety.service.LessonService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicLessonService implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentsGroupRepository studentsGroupRepository;
    @Autowired
    private AppUserRepository appUserRepository;

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
    public List<Lesson> getLessonsFromClassroomId(Long id) {
        return lessonRepository.findByClassroomId(id);
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

    @Transactional
    public List<Lesson> getLessonsFromLecturerSurname(String name) throws ResourceNotFoundException {
        List<Lesson> lessons = lessonRepository.findByLecturer(appUserRepository.findBySurname(name));
        if (lessons.size() > 0) {
            return lessons;
        } else {
            throw new ResourceNotFoundException("No lessons found for lecturer: " + name);
        }
    }

    @Transactional
    public List<Lesson> getLessonsFromLecturerNameAndSurname(String name, String surname) throws
            ResourceNotFoundException {
        List<Lesson> lessonsFromName = lessonRepository.findByLecturer(appUserRepository.findByName(name));
        List<Lesson> lessonsFromSurname = lessonRepository.findByLecturer(appUserRepository.findBySurname(surname));
        List<Lesson> lessonsFromNameAndSurname = lessonsFromName.stream().distinct()
                .filter(lessonsFromSurname::contains)
                .collect(Collectors.toList());
        if (lessonsFromNameAndSurname.size() > 0) {
            return lessonsFromNameAndSurname;
        } else {
            throw new ResourceNotFoundException("No lessons found for lecturer: " + name + " " + surname);
        }
    }
}
