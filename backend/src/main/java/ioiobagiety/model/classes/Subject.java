package ioiobagiety.model.classes;

import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Subject")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private List<StudentsGroup> studentsGroups;
    @OneToMany
    private List<AppUser> lecturers;

}
