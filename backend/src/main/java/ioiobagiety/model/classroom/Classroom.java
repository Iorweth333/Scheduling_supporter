package ioiobagiety.model.classroom;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Classroom")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String building;
    private ClassroomType classroomType;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Classroom))
            return false;

        Classroom cr = (Classroom) o;

        return cr.id.equals(id);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode();
    }
}
