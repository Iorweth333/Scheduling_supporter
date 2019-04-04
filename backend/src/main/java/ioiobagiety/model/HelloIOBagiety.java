package ioiobagiety.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HelloIOBagiety")
public class HelloIOBagiety implements java.io.Serializable {

    @Id
    private Long id;
    private String name;

    public HelloIOBagiety() {
    }

    public HelloIOBagiety(String name) {
        this.name = name;
    }
}
