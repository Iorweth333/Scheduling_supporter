package ioiobagiety.model.classes;

import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.AppUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private List<StudentsGroup> studentsGroups;
    @OneToMany
    private List<AppUser> lecturers;

    public Subject() {

    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
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

    public List<AppUser> getLecturers () {
        return lecturers;
    }

    public void setLecturers (List<AppUser> lecturers) {
        this.lecturers = lecturers;
    }
}
