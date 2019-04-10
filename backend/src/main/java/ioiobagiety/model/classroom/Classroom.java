package ioiobagiety.model.classroom;

import javax.persistence.*;

@Entity
@Table(name = "Classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String building;
    private ClasroomType clasroomType;

    public Classroom() {

    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getNumber () {
        return number;
    }

    public void setNumber (String number) {
        this.number = number;
    }

    public String getBuilding () {
        return building;
    }

    public void setBuilding (String building) {
        this.building = building;
    }

    public ClasroomType getClasroomType () {
        return clasroomType;
    }

    public void setClasroomType (ClasroomType clasroomType) {
        this.clasroomType = clasroomType;
    }
}
