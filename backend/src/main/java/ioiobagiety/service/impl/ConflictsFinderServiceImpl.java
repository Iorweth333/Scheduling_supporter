package ioiobagiety.service.impl;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.ConflictsFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ioiobagiety.repository.LessonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<?> getAll(){
        List dates = getDates();
        List<String> conflicts= new ArrayList<String>();
        for(Object day: dates){
            List lessons = getSingleDayLessons((Date) day);
            for(int i = 0; i < lessons.size(); i++){
                for (int k = ++i; k < lessons.size(); k++) {
                    Lesson l1 = (Lesson) lessons.get(i);
                    Lesson l2 = (Lesson) lessons.get(k);
                    if(l2.getStartsAt().isBefore(l1.getEndsAt())) {
                        if(l1.getLecturer() == l2.getLecturer()) {
                            String conflict = "Lecturer conflict! Lesson id1: " + l1.getId() + "Lesson id2:" + l2.getId();
                            conflicts.add(conflict);
                        }
                        else if(l1.getStudentsGroup() == l2.getStudentsGroup()){
                            String conflict = "Students Group conflict! Lesson id1: " + l1.getId() + "Lesson id2:" + l2.getId();
                            conflicts.add(conflict);
                        }
                        else if(l1.getClassroom() == l2.getClassroom()){
                            String conflict = "Classroom conflict! Lesson id1: " + l1.getId() + "Lesson id2:" + l2.getId();
                            conflicts.add(conflict);
                        }
                    }
                }
            }
        }
        return conflicts;
    }
}
