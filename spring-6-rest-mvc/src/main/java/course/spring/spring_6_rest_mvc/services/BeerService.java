package course.spring.spring_6_rest_mvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import course.spring.spring_6_rest_mvc.model.BeerDTO;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);

    Boolean deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
