package ioiobagiety.repository;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByScheduleName(String name);

    List<Lesson> findByStudentsGroup(StudentsGroup studentsGroup);
    
    List<Lesson> findByLecturer(AppUser appUser);
  
    List<Lesson> findByClassroomId(Long id);

    @Query(value = "SELECT date FROM lesson GROUP BY date", nativeQuery = true)
    List<Date> getDates();

    @Query(value = "SELECT * FROM lesson WHERE date = :d ORDER BY starts_at", nativeQuery = true)
    List<Lesson> getSingleDayLessons(@Param("d") Date date);

    }

