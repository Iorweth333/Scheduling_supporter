package ioiobagiety.model.group;

import ioiobagiety.model.user.AppUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "StudentsGroup")
public class StudentsGroup {

    @Id
    private Long id;
    private String name;
    private String fieldOfStudy;
    private String yearOfStudy;
    private CourseType courseType;
    @OneToMany
    private List<AppUser> students;

    public StudentsGroup () {

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

    public List<AppUser> getStudents () {
        return students;
    }

    public void setStudents (List<AppUser> students) {
        this.students = students;
    }
}
