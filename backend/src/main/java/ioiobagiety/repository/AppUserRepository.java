package ioiobagiety.repository;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
