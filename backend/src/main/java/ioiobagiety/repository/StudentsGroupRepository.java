package ioiobagiety.repository;

import ioiobagiety.model.group.StudentsGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsGroupRepository extends JpaRepository<StudentsGroup, Long> {
    StudentsGroup findByName(String name);
}
