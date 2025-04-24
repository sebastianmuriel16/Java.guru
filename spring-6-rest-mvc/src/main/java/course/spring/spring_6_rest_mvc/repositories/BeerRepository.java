package course.spring.spring_6_rest_mvc.repositories;

import course.spring.spring_6_rest_mvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer,UUID> {

    List<Beer> findAllByBeerNameContaining(String beerName);
}
