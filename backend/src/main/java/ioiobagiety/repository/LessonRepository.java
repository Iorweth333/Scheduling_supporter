package ioiobagiety.repository;

import ioiobagiety.model.classes.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByScheduleName(String name);
}
