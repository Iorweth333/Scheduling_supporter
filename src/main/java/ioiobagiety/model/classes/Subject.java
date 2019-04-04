package ioiobagiety.model.classes;

import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Subject")
public class Subject {

    @Id
    private Long id;
    private String name;
    @OneToMany
    private List<StudentsGroup> studentsGroups;
    @OneToMany
    private List<User> lecturers;

    public Subject() {

    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<StudentsGroup> getStudentsGroups () {
        return studentsGroups;
    }

    public void setStudentsGroups (List<StudentsGroup> studentsGroups) {
        this.studentsGroups = studentsGroups;
    }

    public List<User> getLecturers () {
        return lecturers;
    }

    public void setLecturers (List<User> lecturers) {
        this.lecturers = lecturers;
    }
}
