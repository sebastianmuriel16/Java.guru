package course.spring.spring_6_rest_mvc.services;

import java.util.List;
import java.util.UUID;
import course.spring.spring_6_rest_mvc.model.BeerDTO;

public interface BeerService {

    List<BeerDTO> listBeers();

    BeerDTO getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID id, BeerDTO beer);

    void deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
