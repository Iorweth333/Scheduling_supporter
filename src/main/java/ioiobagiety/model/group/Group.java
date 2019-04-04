package ioiobagiety.model.group;

import ioiobagiety.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Group")
public class Group {

    @Id
    private Long id;
    private String name;
    private String fieldOfStudy;
    private String yearOfStudy;
    private CourseType courseType;
    @OneToMany
    private List<User> students;

    public Group() {

    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getFieldOfStudy () {
        return fieldOfStudy;
    }

    public void setFieldOfStudy (String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getYearOfStudy () {
        return yearOfStudy;
    }

    public void setYearOfStudy (String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public CourseType getCourseType () {
        return courseType;
    }

    public void setCourseType (CourseType courseType) {
        this.courseType = courseType;
    }

    public List<User> getStudents () {
        return students;
    }

    public void setStudents (List<User> students) {
        this.students = students;
    }
}
