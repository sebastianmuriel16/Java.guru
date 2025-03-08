package course.spring.spring_6_rest_mvc.services;

import java.util.List;
import java.util.UUID;
import course.spring.spring_6_rest_mvc.model.Beer;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID id,Beer beer);

    void deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId,Beer beer);
}
