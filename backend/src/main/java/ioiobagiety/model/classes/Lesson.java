package ioiobagiety.model.classes;

import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Lesson")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
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

    public Long getId() {
        return id;
    }

    public Time getStartsAt() {
        return startsAt;
    }

    public Time getEndsAt() {
        return endsAt;
    }

    public AppUser getLecturer() {
        return lecturer;
    }

    public StudentsGroup getStudentsGroup() {
        return studentsGroup;
    }

    public Classroom getClassroom() {
        return classroom;
    }

}
