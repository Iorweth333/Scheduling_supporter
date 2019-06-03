package ioiobagiety.repository;

import ioiobagiety.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
     AppUser findBySurname(String name);
     
     AppUser findByName(String name);
}
