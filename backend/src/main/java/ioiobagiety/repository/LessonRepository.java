package ioiobagiety.repository;

import ioiobagiety.model.classes.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByScheduleName(String name);

    List<Lesson> findByClassroomId(Long id);
}
