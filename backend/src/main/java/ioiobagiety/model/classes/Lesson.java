package ioiobagiety.model.classes;

import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.AppUser;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private Time startsAt;
    private Time endsAt;
    private Integer meetingNumber;
    private String scheduleName;
    @OneToOne(cascade = {CascadeType.ALL})
    private Subject subject;
    @OneToOne(cascade = {CascadeType.ALL})
    private AppUser lecturer;
    @OneToOne(cascade = {CascadeType.ALL})
    private Classroom classroom;
    @OneToOne(cascade = {CascadeType.ALL})
    private StudentsGroup studentsGroup;

    public Lesson () {

    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
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

    public Integer getMeetingNumber () {
        return meetingNumber;
    }

    public void setMeetingNumber (Integer meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public String getScheduleName () {
        return scheduleName;
    }

    public void setScheduleName (String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Subject getSubject () {
        return subject;
    }

    public void setSubject (Subject subject) {
        this.subject = subject;
    }

    public AppUser getLecturer () {
        return lecturer;
    }

    public void setLecturer (AppUser lecturer) {
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
