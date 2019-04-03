package ioiobagiety.model.classroom;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Classroom")
public class Classroom {

    @Id
    private Long id;
    private String number;
    private String building;
    private ClasroomType clasroomType;

    public Classroom() {

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
