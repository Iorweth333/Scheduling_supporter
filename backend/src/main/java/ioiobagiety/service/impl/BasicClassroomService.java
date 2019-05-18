package ioiobagiety.service.impl;

import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.repository.ClassroomRepository;
import ioiobagiety.repository.LessonRepository;
import ioiobagiety.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicClassroomService implements ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public Classroom create(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom get(Long id) {
        return classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
    }

    @Override
    public List<Classroom> getAll() {
        List<Classroom> classrooms = classroomRepository.findAll();
        if (classrooms.size() > 0) {
            return classrooms;
        } else {
            throw new ResourceNotFoundException("No classrooms found");
        }
    }
}
