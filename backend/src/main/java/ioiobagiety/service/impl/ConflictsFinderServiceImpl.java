package ioiobagiety.service.impl;

@Service
public class ConflictsFinderServiceImpl implements ConflictsFinderService {

    @Autowired
    private LessonRepository repository;

    @Override
    public List<Date> getDates() {
        return repository.getDates();
    }

    @Override
    public List<Lesson> getSingleDayLessons(Date date) {
        return repository.getSingleDayLessons(Date date);
    }

    @Transactional
    public List<?> getAll(){
        List dates = getDates();
        List<String> conflicts= new ArrayList<String>();
        for(Date day: dates){
            List lessons = getSingleDayLessons(day);
            for(Lesson l: lessons){
                for (int i = ++indexOf(l); i < lessons.size(); i++) {
                    l2 = lessons.get(i);
                    if(l2.startsAt.isBefore(l.endsAt)) {
                        if(l.lecturer == l2.lecturer) {
                            String conflict = "Lecturer conflict! Lesson id1: " + l.id + "Lesson id2:" + l2.id;
                            conflicts.add(conflict);
                        }
                        else if(l.studentsGroup == l2.studentsGroup){
                            String conflict = "Students Group conflict! Lesson id1: " + l.id + "Lesson id2:" + l2.id;
                            conflicts.add(conflict);
                        }
                        else if(l.classroom == l2.classroom){
                            String conflict = "Classroom conflict! Lesson id1: " + l.id + "Lesson id2:" + l2.id;
                            conflicts.add(conflict);
                        }
                    }
                }
            }
        }
        return conflicts;
    }
}
