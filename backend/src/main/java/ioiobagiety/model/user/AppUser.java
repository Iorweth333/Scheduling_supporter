package ioiobagiety.model.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "AppUser")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String academicTitle;
    private String login;
    private String password;
    private String email;
    private UserType userType;

}
