package ioiobagiety.model.classes;

import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Class")
public class Class {

    @Id
    private Long id;
    private Date date;
    private Time startsAt;
    private Time endsAt;
    @OneToOne
    private Subject subject;
    @OneToOne
    private User lecturer;
    @OneToOne
    private Classroom classroom;
    @OneToOne
    private StudentsGroup studentsGroup;

    public Class() {

    }

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public Time getStartsAt () {
        return startsAt;
    }

    public void setStartsAt (Time startsAt) {
        this.startsAt = startsAt;
    }

    public Time getEndsAt () {
        return endsAt;
    }

    public void setEndsAt (Time endsAt) {
        this.endsAt = endsAt;
    }

    public Subject getSubject () {
        return subject;
    }

    public void setSubject (Subject subject) {
        this.subject = subject;
    }

    public User getLecturer () {
        return lecturer;
    }

    public void setLecturer (User lecturer) {
        this.lecturer = lecturer;
    }

    public Classroom getClassroom () {
        return classroom;
    }

    public void setClassroom (Classroom classroom) {
        this.classroom = classroom;
    }

    public StudentsGroup getStudentsGroup () {
        return studentsGroup;
    }

    public void setStudentsGroup (StudentsGroup studentsGroup) {
        this.studentsGroup = studentsGroup;
    }
}
