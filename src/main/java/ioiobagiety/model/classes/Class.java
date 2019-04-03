package ioiobagiety.model.classes;

import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.group.Group;
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
    private Group group;
}
