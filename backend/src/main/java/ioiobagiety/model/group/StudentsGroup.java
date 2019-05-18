package ioiobagiety.model.group;

import ioiobagiety.model.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "StudentsGroup")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class StudentsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String fieldOfStudy;
    private String yearOfStudy;
    private CourseType courseType;
    @OneToMany
    private List<AppUser> students;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StudentsGroup))
            return false;

        StudentsGroup group = (StudentsGroup) o;

        return group.id.equals(id);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode();
    }
}
