package ioiobagiety.service.impl;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.response.Conflict;
import ioiobagiety.service.ConflictsFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ioiobagiety.repository.LessonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.sql.Time;

@Service
public class ConflictsFinderServiceImpl implements ConflictsFinderService {

    @Autowired
    private LessonRepository repository;

    @Transactional
    public List<Date> getDates() {
        return repository.getDates();
    }

    @Transactional
    public List<Lesson> getSingleDayLessons(Date date) {
        return repository.getSingleDayLessons(date);
    }

    @Transactional
    public List<Conflict> getAllConflicts(){
        List<Date> dates = getDates();
        List<Conflict> conflicts = new ArrayList<>();
        for(Date day: dates){
            List<Lesson> lessons = getSingleDayLessons(day);
            for(int i = 0; i < lessons.size(); i++){
                for (int k = i+1; k < lessons.size(); k++) {
                    Lesson lesson1 = lessons.get(i);
                    Lesson lesson2 = lessons.get(k);
                    Time sqlLesson2Start = lesson2.getStartsAt();
                    LocalTime lesson2Start = LocalTime.MIDNIGHT.plus(sqlLesson2Start.getTime(), ChronoUnit.MILLIS);
                    Time sqlLesson1End = lesson1.getEndsAt();
                    LocalTime lesson1End = LocalTime.MIDNIGHT.plus(sqlLesson1End.getTime(), ChronoUnit.MILLIS);
                    if(lesson2Start.isBefore(lesson1End)) {
                        if(lesson1.getLecturer().equals(lesson2.getLecturer())) {
                            Conflict conflict = new Conflict(Conflict.conflictType.LECTURER, lesson1.getId(), lesson2.getId());
                            conflicts.add(conflict);
                        }
                        if(lesson1.getStudentsGroup().equals(lesson2.getStudentsGroup())) {
                            Conflict conflict = new Conflict(Conflict.conflictType.GROUP, lesson1.getId(), lesson2.getId());
                            conflicts.add(conflict);
                        }
                        if(lesson1.getClassroom().equals(lesson2.getClassroom())) {
                            Conflict conflict = new Conflict(Conflict.conflictType.CLASSROOM, lesson1.getId(), lesson2.getId());
                            conflicts.add(conflict);
                        }
                    }
                }
            }
        }
        return conflicts;
    }
}
