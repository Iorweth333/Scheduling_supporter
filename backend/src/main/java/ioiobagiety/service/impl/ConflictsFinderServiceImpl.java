package ioiobagiety.service.impl;

import ioiobagiety.model.classes.Lesson;
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
    public List<String> getAllConflicts(){
        List dates = getDates();
        List<String> conflicts = new ArrayList<String>();
        for(Object day: dates){
            List lessons = getSingleDayLessons((Date) day);
            for(int i = 0; i < lessons.size(); i++){
                for (int k = i+1; k < lessons.size(); k++) {
                    Lesson lesson1 = (Lesson) lessons.get(i);
                    Lesson lesson2 = (Lesson) lessons.get(k);
                    Time sqlLesson2Start = lesson2.getStartsAt();
                    LocalTime lesson2start = LocalTime.MIDNIGHT.plus(sqlLesson2Start.getTime(), ChronoUnit.MILLIS);
                    Time sqlLesson1End = lesson1.getEndsAt();
                    LocalTime lesson1end = LocalTime.MIDNIGHT.plus(sqlLesson1End.getTime(), ChronoUnit.MILLIS);
                    if(lesson2start.isBefore(lesson1end)) {
                        if(lesson1.getLecturer() == lesson2.getLecturer()) {
                            String conflict = "Lecturer conflict! Lesson id1: " + lesson1.getId() + " Lesson id2: " + lesson2.getId();
                            conflicts.add(conflict);
                        }
                        if(lesson1.getStudentsGroup() == lesson2.getStudentsGroup()){
                            String conflict = "Students Group conflict! Lesson id1: " + lesson1.getId() + " Lesson id2: " + lesson2.getId();
                            conflicts.add(conflict);
                        }
                        if(lesson1.getClassroom() == lesson2.getClassroom()){
                            String conflict = "Classroom conflict! Lesson id1: " + lesson1.getId() + "Lesson id2: " + lesson2.getId();
                            conflicts.add(conflict);
                        }
                    }
                }
            }
        }
        return conflicts;
    }
}
