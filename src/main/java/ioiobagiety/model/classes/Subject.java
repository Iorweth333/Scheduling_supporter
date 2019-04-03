package ioiobagiety.model.classes;

import ioiobagiety.model.group.Group;
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
    private List<Group> groups;
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

    public List<Group> getGroups () {
        return groups;
    }

    public void setGroups (List<Group> groups) {
        this.groups = groups;
    }

    public List<User> getLecturers () {
        return lecturers;
    }

    public void setLecturers (List<User> lecturers) {
        this.lecturers = lecturers;
    }
}
