package ioiobagiety.repository;

import java.util.Optional;

import ioiobagiety.model.HelloIOBagiety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HelloIOBagietyRepository extends JpaRepository<HelloIOBagiety, Long>{
    public Optional<HelloIOBagiety> findByName(String name);

    @Query("SELECT b FROM HelloIOBagiety b WHERE LOWER(b.name) = 'bagieta 1'")
    public Optional<HelloIOBagiety> getByCustomQuery();
}
