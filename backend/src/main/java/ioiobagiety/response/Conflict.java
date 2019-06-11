package ioiobagiety.response;

import ioiobagiety.model.classes.Lesson;

public class Conflict {

    public enum conflictType {
        GROUP, LECTURER, CLASSROOM
    }
    private conflictType typeOfConflict;
    private Lesson lesson1;
    private Lesson lesson2;
    /*private long lessonId1;
    private long lessonId2;*/

    /*public Conflict(conflictType typeOfConflict, long lessonId1, long lessonId2){
        this.typeOfConflict = typeOfConflict;
        this.lessonId1 = lessonId1;
        this.lessonId2 = lessonId2;
    }*/

    public Conflict(conflictType typeOfConflict, Lesson lesson1, Lesson lesson2) {
        this.typeOfConflict = typeOfConflict;
        this.lesson1 = lesson1;
        this.lesson2 = lesson2;
    }

}
