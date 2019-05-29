package ioiobagiety.response;

public class Conflict {

    public enum conflictType {
        GROUP, LECTURER, CLASSROOM
    }
    private conflictType typeOfConflict;
    private long lessonId1;
    private long lessonId2;

    public Conflict(conflictType typeOfConflict, long lessonId1, long lessonId2){
        this.typeOfConflict = typeOfConflict;
        this.lessonId1 = lessonId1;
        this.lessonId2 = lessonId2;
    }

}
