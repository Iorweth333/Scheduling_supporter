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
    private ClasroomType clasroomType;

}
